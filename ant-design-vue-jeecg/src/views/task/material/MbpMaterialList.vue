<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('物料表')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="pcaSlot" slot-scope="text">
          <div>{{ getPcaText(text) }}</div>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <mbp-material-modal ref="modalForm" @ok="modalFormOk"></mbp-material-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import MbpMaterialModal from './modules/MbpMaterialModal'
  import { loadCategoryData } from '@/api/api'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import Area from '@/components/_util/Area'

  export default {
    name: 'MbpMaterialList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      MbpMaterialModal
    },
    data () {
      return {
        description: '物料表管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'物料编码',
            align:"center",
            dataIndex: 'materialId'
          },
          {
            title:'物料名称',
            align:"center",
            dataIndex: 'materialName'
          },
          /*{
            title:'别名',
            align:"center",
            dataIndex: 'alias'
          },
          {
            title:'英文名',
            align:"center",
            dataIndex: 'englishName'
          },
          {
            title:'检索码',
            align:"center",
            dataIndex: 'indexCode'
          },
          {
            title:'等级',
            align:"center",
            dataIndex: 'level'
          },*/
          {
            title:'型号',
            align:"center",
            dataIndex: 'model'
          },
          {
            title:'规格',
            align:"center",
            dataIndex: 'special'
          },
          /*{
            title:'通用规格',
            align:"center",
            dataIndex: 'generalSpecial'
          },
          {
            title:'商标',
            align:"center",
            dataIndex: 'brand'
          },
          {
            title:'ABC类',
            align:"center",
            dataIndex: 'abcCategory'
          },*/
          {
            title:'颜色',
            align:"center",
            dataIndex: 'color'
          },
          /*{
            title:'计算特性1',
            align:"center",
            dataIndex: 'spe1'
          },
          {
            title:'计算特性2',
            align:"center",
            dataIndex: 'spe2'
          },
          {
            title:'条形码',
            align:"center",
            dataIndex: 'barcode'
          },*/
          {
            title:'物料分类编码',
            align:"center",
            dataIndex: 'materialTypeEncode',
            customRender: (text) => (text ? filterMultiDictText(this.dictOptions['materialTypeEncode'], text) : '')
          },
          {
            title:'单位',
            align:"center",
            dataIndex: 'unitid'
          },
          /*{
            title:'是否虚拟件0-否,1-是',
            align:"center",
            dataIndex: 'isVirtalPart_dictText'
          },
          {
            title:'是否外购件0-否,1-是',
            align:"center",
            dataIndex: 'isPurchasePart_dictText'
          },
          {
            title:'是否通用件0-否,1-是',
            align:"center",
            dataIndex: 'isGeneralPart_dictText'
          },
          {
            title:'安全库存',
            align:"center",
            dataIndex: 'minWhStorge'
          },
          {
            title:'最高库存',
            align:"center",
            dataIndex: 'maxWhStorge'
          },
          {
            title:'当前库存',
            align:"center",
            dataIndex: 'currentWhStogre'
          },
          {
            title:'允许销售0-禁止,1-允许',
            align:"center",
            dataIndex: 'isSell'
          },
          {
            title:'序列号',
            align:"center",
            dataIndex: 'serialNumber'
          },
          {
            title:'采购单位',
            align:"center",
            dataIndex: 'purchaseOrganize'
          },
          {
            title:'产地',
            align:"center",
            dataIndex: 'productAddress',
            scopedSlots: {customRender: 'pcaSlot'}
          },
          {
            title:'是否入库检验',
            align:"center",
            dataIndex: 'isWhInSpect_dictText'
          },
          {
            title:'成本日期',
            align:"center",
            dataIndex: 'costDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'标准成本价',
            align:"center",
            dataIndex: 'standardCost'
          },
          {
            title:'销售价',
            align:"center",
            dataIndex: 'salesCost'
          },
          {
            title:'采购价',
            align:"center",
            dataIndex: 'purchaseCost'
          },
          {
            title:'采购提前天数',
            align:"center",
            dataIndex: 'prepareDays'
          },
          {
            title:'工序编号',
            align:"center",
            dataIndex: 'processId'
          },
          {
            title:'是否停用0-否,1-是',
            align:"center",
            dataIndex: 'isStop_dictText'
          },
          {
            title:'起初数值',
            align:"center",
            dataIndex: 'startNumber'
          },*/
          {
            title:'图片',
            align:"center",
            dataIndex: 'figurePath',
            scopedSlots: {customRender: 'imgSlot'}
          },
          {
            title:'备注',
            align:"center",
            dataIndex: 'disp'
          },
          /*{
            title:'创建人',
            align:"center",
            dataIndex: 'createBy'
          },
          {
            title:'创建时间',
            align:"center",
            dataIndex: 'createTime'
          },
          {
            title:'修改人',
            align:"center",
            dataIndex: 'updateBy'
          },
          {
            title:'修改时间',
            align:"center",
            dataIndex: 'updateTime'
          },
          {
            title:'删除标识0-正常,1-已删除',
            align:"center",
            dataIndex: 'delFlag_dictText'
          },*/
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/system/mbpMaterial/list",
          delete: "/system/mbpMaterial/delete",
          deleteBatch: "/system/mbpMaterial/deleteBatch",
          exportXlsUrl: "/system/mbpMaterial/exportXls",
          importExcelUrl: "system/mbpMaterial/importExcel",

        },
        dictOptions:{},
        pcaData:''
      }
    },
    created() {
      this.pcaData = new Area()
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      getPcaText(code){
        return this.pcaData.getText(code);
      },
      initDictConfig(){
        loadCategoryData({code:'B06'}).then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'materialTypeEncode', res.result)
          }
        })
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>