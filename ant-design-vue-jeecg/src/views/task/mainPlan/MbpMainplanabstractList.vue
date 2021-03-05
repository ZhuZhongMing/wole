<template>
  <a-card class="j-inner-table-wrapper" :bordered="false">

    <!-- 查询区域 begin -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="订单编号">
              <!--<a-select v-model="queryParam.orderId" :trigger-change="true" placeholder="请选择订单编号">
                <a-select-option v-for="(item, key) in orderList" :key="key" :value="item.id">{{item.orderTitle}}</a-select-option>
              </a-select>-->
              <j-dict-select-tag v-model="queryParam.orderId" placeholder="请选择订单"
                                 dictCode="mbp_order,order_title,id"/>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="开始日期">
              <!--<a-input v-model="queryParam.orderId" placeholder="请输入订单编号"/>-->
              <!--<a-date-picker placeholder="请选择创建时间" v-model="queryParam.createTime" style="width: 100%"/>-->
              <j-date placeholder="请选择创建时间" v-model="queryParam.createTime_begin" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="结束日期">
              <!--<a-input v-model="queryParam.orderId" placeholder="请输入订单编号"/>-->
              <!--<a-date-picker placeholder="请选择创建时间" v-model="queryParam.createTime" style="width: 100%"/>-->
              <j-date placeholder="请选择创建时间" v-model="queryParam.createTime_end" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <!--<a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="订单标题">
                <a-input v-model="queryParam.orderTitle" placeholder="请输入订单标题"/>
              </a-form-item>
            </a-col>-->
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span class="table-page-search-submitButtons table-operator">
              <a-button type="primary" icon="search" @click="searchQuery">查询</a-button>
              <a-button type="primary" icon="reload" @click="searchReset">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                <span>{{ toggleSearchStatus ? '收起' : '展开' }}</span>
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域 end -->

    <!-- 操作按钮区域 begin -->
    <div class="table-operator">
      <a-button type="primary" icon="plus" @click="handleAdd">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('生产计划')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>

      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel">
            <a-icon type="delete"/>
            <span>删除</span>
          </a-menu-item>
        </a-menu>
        <a-button>
          <span>批量操作</span>
          <a-icon type="down"/>
        </a-button>
      </a-dropdown>
    </div>
    <!-- 操作按钮区域 end -->

    <!-- table区域 begin -->
    <div>

      <a-alert type="info" showIcon style="margin-bottom: 16px;">
        <template slot="message">
          <span>已选择</span>
          <a style="font-weight: 600;padding: 0 4px;">{{ selectedRowKeys.length }}</a>
          <span>项</span>
          <a style="margin-left: 24px" @click="onClearSelected">清空</a>
        </template>
      </a-alert>
      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        class="j-table-force-nowrap"
        :scroll="{x:true}"
        :loading="loading"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :expandedRowKeys="expandedRowKeys"
        :rowSelection="{selectedRowKeys, onChange: onSelectChange}"
        @expand="handleExpand"
        @change="handleTableChange"
      >

        <!-- 内嵌table区域 begin -->
        <template slot="expandedRowRender" slot-scope="record">
          <a-tabs tabPosition="top">
            <a-tab-pane tab="生产计划明细" key="mbpMainplan" forceRender>
              <mbp-mainplan-sub-table :record="record"/>
            </a-tab-pane>
          </a-tabs>
        </template>
        <!-- 内嵌table区域 end -->

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>

        <template slot="imgSlot" slot-scope="text">
          <div style="font-size: 12px;font-style: italic;">
            <span v-if="!text">无图片</span>
            <img v-else :src="getImgView(text)" alt="" style="max-width:80px;height:25px;"/>
          </div>
        </template>

        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            ghost
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)"
          >
            <span>下载</span>
          </a-button>
        </template>

        <template slot="bomSlot" slot-scope="text, record">
          <a-button
            :ghost="true"
            type="primary"
            icon="search"
            size="small"
            @click="searchBOM(record)">
            查看BOM
          </a-button>
        </template>

        <template slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical"/>
          <a-dropdown>
            <a class="ant-dropdown-link">
              <span>更多 <a-icon type="down"/></span>
            </a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>

        </template>

      </a-table>
    </div>
    <!-- table区域 end -->

    <!-- 表单区域 -->
    <mbp-mainplanabstract-modal ref="modalForm" @ok="modalFormOk"/>

    <!-- BOM -->
    <!--title="产品BOM表"-->
    <a-modal
      title="产品物料BOM"
      :visible="visibleBom"
      @cancel="handleCloseBom"
      :footer="null"
      :width="bomWidth"
    >
      <div class="no-print" style="text-align: right">
        <a-button v-print="'#printContent'" ghost type="primary">打印</a-button>
      </div>
      <table ref="print"
             id="printContent">
        <thead>
        <tr>
          <td colspan="6"><b style="font-size: 20px">{{title}}</b></td>
        </tr>
        </thead>
        <tbody v-for="(i,index) in bom" :key="index">
        <tr>
          <td><b>产品名称</b></td>
          <td v-text="i.productName"></td>
          <td><b>产品型号</b></td>
          <td v-text="i.productType"></td>
          <td><b>产品规格</b></td>
          <td v-text="i.productSize"></td>
        </tr>
        <tr>
          <td><b>产品颜色</b></td>
          <td v-text="i.productColor"></td>
          <td><b>计划数量</b></td>
          <td v-text="i.number"></td>
          <td><b>产品长度</b></td>
          <td v-text="i.disp"></td>
        </tr>
        <tr>
          <td colspan="6" style="text-align: left; padding-left: 30px;"><b>配件数量</b></td>
        </tr>
        <tr v-for="(item,key) in i.materialList" :key="key">
          <td><b v-text="item.materialName"></b></td>
          <td colspan="5" v-text="item.number * i.number"></td>

        </tr>
        <!--<tr>
          <td><b></b></td>
          <td v-text="i.number * 4"></td>
          <td><b></b></td>
          <td v-text="i.number"></td>
          <td><b></b></td>
          <td v-text="i.number * 4"></td>
        </tr>-->
        <tr v-if="(index+1) != bom.length">
          <td colspan="6" style="color:  rgba(0,0,0,0); background-color: #8cc8ff;">&nbsp;&nbsp;&nbsp;</td>
        </tr>
        </tbody>
      </table>


    </a-modal>

  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import MbpMainplanabstractModal from './modules/MbpMainplanabstractModal'
  import MbpMainplanSubTable from './subTables/MbpMainplanSubTable'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import '@/assets/less/TableExpand.less'
  import { getAction } from '@/api/manage'
  import JDate from '@/components/jeecg/JDate'


  export default {
    name: 'MbpMainplanabstractList',
    mixins: [JeecgListMixin],
    components: {
      MbpMainplanabstractModal,
      MbpMainplanSubTable,
      JDate
    },
    data() {
      return {
        title:'产品物料BOM清单',
        /*屏幕宽度*/
        bomWidth:window.screen.width * 0.7,
        visibleBom:false,
        bom:[],
        printer:'张三',
        printTime:'2019-02-01 12:00:00',
        printContent:'打印内容就是,做一个打印测试',
        printReason:'做一个打印测试',
        previewVisible: false,
        previewImage: '',
        fileList: [{
          uid: '-1',
          name: 'xxx.png',
          status: 'done',
          url: 'https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png',
        },
          {
            uid:'-2',
            name:'pic1.png',
            status:'done',
            url:'https://www.gizbot.com/img/2016/11/whatsapp-error-lead-image-08-1478607387.jpg',
          }
        ],
        description: '生产计划列表管理页面',
        // 表头
        columns: [
          {
            title: '#',
            key: 'rowIndex',
            width: 60,
            align: 'center',
            customRender: (t, r, index) => parseInt(index) + 1
          },
          {
            title: '部门编号',
            align: 'center',
            dataIndex: 'deptId_dictText'
          },
          {
            title: '订单编号',
            align: 'center',
            dataIndex: 'orderId_dictText'
          },
          /*{
            title: '业务员编号',
            align: 'center',
            dataIndex: 'salesmanId_dictText'
          },
          {
            title: '计划种类编号',
            align: 'center',
            dataIndex: 'plantypeId',
          },
          {
            title: '业务日期',
            align: 'center',
            dataIndex: 'saleDate',
          },
          {
            title: '审核人编号',
            align: 'center',
            dataIndex: 'auditorId_dictText'
          },
          {
            title: '审核日期',
            align: 'center',
            dataIndex: 'auditDate',
          },
          {
            title: '审核状态',
            align: 'center',
            dataIndex: 'auditStatus_dictText'
          },*/
          {
            title: '备注',
            align: 'center',
            dataIndex: 'disp',
          },
          {
            title: '创建人',
            align: 'center',
            dataIndex: 'createBy',
          },
          {
            title: '制单日期',
            align: 'center',
            dataIndex: 'createTime',
          },
          {
            title: '修改人',
            align: 'center',
            dataIndex: 'updateBy',
          },
          {
            title: '修改时间',
            align: 'center',
            dataIndex: 'updateTime',
          },
          /*{
            title: '删除标识0-正常,1-已删除',
            align: 'center',
            dataIndex: 'delFlag_dictText'
          },*/
          /*{
            title:'BOM',
            align:"center",
            dataIndex: 'bomSlot',
            scopedSlots: {customRender: "bomSlot"}
          },*/
          {
            title: '操作',
            dataIndex: 'action',
            align: 'center',
            width:147,
            scopedSlots: { customRender: 'action' },
          },
        ],
        // 字典选项
        dictOptions: {},
        // 展开的行test
        expandedRowKeys: [],
        url: {
          list: '/system/mbpMainplanabstract/list',
          delete: '/system/mbpMainplanabstract/delete',
          deleteBatch: '/system/mbpMainplanabstract/deleteBatch',
          exportXlsUrl: '/system/mbpMainplanabstract/exportXls',
          importExcelUrl: '/system/mbpMainplanabstract/importExcel',
          queryListByMainId: '/system/mbpMainplanabstract/queryListByMainId',
          queryBom: '/system/mbpMainplanabstract/queryBom'
        },
      }
    },
    computed: {
      importExcelUrl() {
        return window._CONFIG['domianURL'] + this.url.importExcelUrl
      }
    },
    methods: {

      initDictConfig() {
      },

      handleExpand(expanded, record) {
        this.expandedRowKeys = []
        if (expanded === true) {
          this.expandedRowKeys.push(record.id)
        }
      },
      /*查看BOM*/
      searchBOM (record) {
        this.loading = true
        getAction(this.url.queryBom,{id: record.id}).then((res) => {
          if (res.success) {
            //this.$set(this.dictOptions, 'deptid', res.result)
            this.bom.splice(0)
            this.bom = res.result
            console.log("result : " + JSON.stringify(res.result))
            setTimeout(()=>{
              this.loading = false
              this.visibleBom = true
            },500)
          } else {
            this.$message.warning('请先设置明细')
          }
        })
      },
      handleCloseBom(e) {
        this.visibleBom = false;
        setTimeout(() => {
          this.bom.splice(0)
        },500);

      },
    }
  }
</script>
<style lang="less" scoped>
  @import '~@assets/less/common.less';

  table,tr,td {
    border: 1px solid black;
  }
  table {
    width: 800px;
    margin: 0px auto;
    text-align: center;
  }
  td {
    line-height: 30px;
    width: 16.6%;
  }
</style>