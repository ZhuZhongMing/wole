<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="客户名称">
              <a-input placeholder="请输入客户名称" v-model="queryParam.customerName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="联系人">
              <a-input placeholder="请输入联系人" v-model="queryParam.contactPerson"></a-input>
            </a-form-item>
          </a-col>
          <!--<a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="联系电话">
              <a-input placeholder="请输入联系电话" v-model="queryParam.telephone"></a-input>
            </a-form-item>
          </a-col>-->
          <!--<a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="检索码">
              <a-input placeholder="请输入检索码" v-model="queryParam.indexCode"></a-input>
            </a-form-item>
          </a-col>-->
          <template v-if="toggleSearchStatus">
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('客户表')">导出</a-button>
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

    <mbp-customer-modal ref="modalForm" @ok="modalFormOk"></mbp-customer-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  /*import MbpCustomerModal from './modules/MbpCustomerModal'*/
  import MbpCustomerModal from './modules/MbpCustomerModal'
  import { loadCategoryData } from '@/api/api'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import Area from '@/components/_util/Area'

  export default {
    name: 'MbpCustomerList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      MbpCustomerModal
    },
    data () {
      return {
        description: '客户表管理页面',
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
            title:'客户名称',
            align:"center",
            dataIndex: 'customerName'
          },
          /*{
            title:'简称',
            align:"center",
            dataIndex: 'abcName'
          },
          {
            title:'检索码',
            align:"center",
            dataIndex: 'indexCode'
          },
          {
            title:'是否供应商0-否,1-是',
            align:"center",
            dataIndex: 'isSupplier_dictText'
          },
          {
            title:'是否客户0-否,1-是',
            align:"center",
            dataIndex: 'isClient_dictText'
          },*/
          {
            title:'客户类型',
            align:"center",
            dataIndex: 'customerType',
            customRender: (text) => (text ? filterMultiDictText(this.dictOptions['customerType'], text) : '')
          },
          /*{
            title:'客户分类1',
            align:"center",
            dataIndex: 'categoryType',
            customRender: (text) => (text ? filterMultiDictText(this.dictOptions['categoryType'], text) : '')
          },*/
          {
            title:'所属区域',
            align:"center",
            dataIndex: 'customerArea',
            scopedSlots: {customRender: 'pcaSlot'}
          },
          /*{
            title:'首次合作时间',
            align:"center",
            dataIndex: 'firstCoDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'客户分类2',
            align:"center",
            dataIndex: 'categoryType2',
            customRender: (text) => (text ? filterMultiDictText(this.dictOptions['categoryType2'], text) : '')
          },
          {
            title:'是否外协0-否,1-是',
            align:"center",
            dataIndex: 'isAssist_dictText'
          },*/
          {
            title:'客户地址',
            align:"center",
            dataIndex: 'customerAddress'
          },
          {
            title:'邮政编码',
            align:"center",
            dataIndex: 'postCode'
          },
          {
            title:'法人',
            align:"center",
            dataIndex: 'customerManager'
          },
          {
            title:'联系人',
            align:"center",
            dataIndex: 'contactPerson'
          },
          {
            title:'联系电话',
            align:"center",
            dataIndex: 'telephone'
          },
          {
            title:'邮箱',
            align:"center",
            dataIndex: 'email'
          },
          /*{
            title:'传真',
            align:"center",
            dataIndex: 'fax'
          },
          {
            title:'开户银行',
            align:"center",
            dataIndex: 'bank'
          },
          {
            title:'开户账号',
            align:"center",
            dataIndex: 'account'
          },
          {
            title:'付款方式',
            align:"center",
            dataIndex: 'paymentKind',
            customRender: (text) => (text ? filterMultiDictText(this.dictOptions['paymentKind'], text) : '')
          },
          {
            title:'付款周期',
            align:"center",
            dataIndex: 'paymentCircle'
          },
          {
            title:'结算货币',
            align:"center",
            dataIndex: 'currency'
          },
          {
            title:'税号',
            align:"center",
            dataIndex: 'taxNo'
          },
          {
            title:'营业执照',
            align:"center",
            dataIndex: 'license'
          },
          {
            title:'信用等级',
            align:"center",
            dataIndex: 'creditGrade'
          },
          {
            title:'信用额度',
            align:"center",
            dataIndex: 'creditLimit'
          },
          {
            title:'业务员',
            align:"center",
            dataIndex: 'salesman_dictText'
          },
          {
            title:'客户启用状态',
            align:"center",
            dataIndex: 'customerStatus_dictText'
          },
          {
            title:'描述',
            align:"center",
            dataIndex: 'description'
          },*/
          {
            title:'备注',
            align:"center",
            dataIndex: 'disp'
          },
          {
            title:'企业网址',
            align:"center",
            dataIndex: 'webSite'
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
          list: "/system/mbpCustomer/list",
          delete: "/system/mbpCustomer/delete",
          deleteBatch: "/system/mbpCustomer/deleteBatch",
          exportXlsUrl: "/system/mbpCustomer/exportXls",
          importExcelUrl: "system/mbpCustomer/importExcel",

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
        loadCategoryData({code:'B03'}).then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'customerType', res.result)
          }
        })
        loadCategoryData({code:'B04'}).then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'categoryType', res.result)
          }
        })
        loadCategoryData({code:'B04'}).then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'categoryType2', res.result)
          }
        })
        loadCategoryData({code:'B05'}).then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'paymentKind', res.result)
          }
        })
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>