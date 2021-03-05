<template>
  <a-card class="j-inner-table-wrapper" :bordered="false">

    <!-- 查询区域 begin -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="客户编号">
              <j-dict-select-tag v-model="queryParam.customerId" placeholder="请选择客户编号"
                                 dictCode="mbp_customer,customer_name,id"/>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="订单编号">
              <a-input v-model="queryParam.orderId" placeholder="请输入订单编号"/>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="订单标题">
                <a-input v-model="queryParam.orderTitle" placeholder="请输入订单标题"/>
              </a-form-item>
            </a-col>
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
      <a-button type="primary" icon="download" @click="handleExportXls('订单')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>

      <!--设置生产计划按钮，一次只能设定一个-->
      <!--<a-button v-if="selectedRowKeys.length == 1 " type="primary" icon="tag" @click="setPlan">指定计划</a-button>-->

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
            <a-tab-pane tab="订单明细" key="mbpOrderlist" forceRender>
              <mbp-orderlist-sub-table :record="record"/>
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


        <template slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical"/>
          <a-dropdown>
            <a class="ant-dropdown-link">
              <span>更多 <a-icon type="down"/></span>
            </a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="setPlan(record)">指定计划</a>
              </a-menu-item>
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
    <mbp-order-modal ref="modalForm" @ok="modalFormOk"/>
    <!-- 生产计划表单 -->
    <mbp-mainplanabstract-modal ref="modalFormOfPlan" @ok="modalFormOk"/>

  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import MbpOrderModal from './modules/MbpOrderModal'
  import MbpOrderlistSubTable from './subTables/MbpOrderlistSubTable'
  import MbpMainplanabstractModal from './modules/MbpMainplanabstractModal'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import { loadCategoryData } from '@/api/api'
  import '@/assets/less/TableExpand.less'


  export default {
    name: 'MbpOrderList',
    mixins: [JeecgListMixin],
    components: {
      MbpOrderModal,
      MbpOrderlistSubTable,
      JDictSelectTag,
      MbpMainplanabstractModal,
    },
    data() {
      return {
        description: '订单列表管理页面',
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
            title: '订单编号',
            align: 'center',
            dataIndex: 'orderId',
          },
          {
            title: '订单标题',
            align: 'center',
            dataIndex: 'orderTitle',
          },
          {
            title: '客户名称',
            align: 'center',
            dataIndex: 'customerId_dictText'
          },
          {
            title: '业务员',
            align: 'center',
            dataIndex: 'salesman_dictText'
          },
          /*{
            title: '部门编号',
            align: 'center',
            dataIndex: 'deptId_dictText'
          },
          {
            title: '支付方式',
            align: 'center',
            dataIndex: 'paymentId',
          },
          {
            title: '支付时间',
            align: 'center',
            dataIndex: 'paymentDate',
          },
          {
            title: '递送方式',
            align: 'center',
            dataIndex: 'deliveryMethod',
          },
          {
            title: '递送费用',
            align: 'center',
            dataIndex: 'deliveryFee',
          },
          {
            title: '审核人',
            align: 'center',
            dataIndex: 'auditPerson',
          },
          {
            title: '审核时间',
            align: 'center',
            dataIndex: 'auditTime',
          },
          {
            title: '结算货币',
            align: 'center',
            dataIndex: 'currency',
          },
          {
            title: '订单类别',
            align: 'center',
            dataIndex: 'orderType',
            customRender: (text) => (text ? filterMultiDictText(this.dictOptions['orderType'], text) : '')
          },
          {
            title: '订单分配状态0-未全部分配,1-已全部分配',
            align: 'center',
            dataIndex: 'orderState_dictText'
          },*/
          {
            title: '合同编号',
            align: 'center',
            dataIndex: 'contract',
          },
          {
            title: '备注',
            align: 'center',
            dataIndex: 'disp',
          },
          {
            title: '合同附件',
            align: 'center',
            dataIndex: 'accessory',
            scopedSlots: {customRender: 'fileSlot'}
          },
          {
            title: '创建人',
            align: 'center',
            dataIndex: 'createBy',
          },
          {
            title: '创建时间',
            align: 'center',
            dataIndex: 'createTime',
          },
          /*{
            title: '修改人',
            align: 'center',
            dataIndex: 'updateBy',
          },
          {
            title: '修改时间',
            align: 'center',
            dataIndex: 'updateTime',
          },
          {
            title: '删除标识0-正常,1-已删除',
            align: 'center',
            dataIndex: 'delFlag_dictText'
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
          list: '/system/mbpOrder/list',
          delete: '/system/mbpOrder/delete',
          deleteBatch: '/system/mbpOrder/deleteBatch',
          exportXlsUrl: '/system/mbpOrder/exportXls',
          importExcelUrl: '/system/mbpOrder/importExcel',
        },
      }
    },
    computed: {
      importExcelUrl() {
        return window._CONFIG['domianURL'] + this.url.importExcelUrl
      }
    },
    methods: {
      /*设置生产计划*/
      setPlan(record) {
        record.orderId = record.id //订单id即为生产计划中订单id
        this.$refs.modalFormOfPlan.edit(record);
        this.$nextTick(()=>{
          this.$refs.modalFormOfPlan.changeOrder(record.id);
        })
        this.$refs.modalFormOfPlan.title = "指定生产计划";
        this.$refs.modalFormOfPlan.disableSubmit = false;
      },
      /*设置计划ok*/
      modalFormOk() {
        // 新增/修改 成功时，重载列表
        this.loadData();
      },
      initDictConfig() {
        loadCategoryData({ code: "B07" }).then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'orderType', res.result)
          }
        })
      },
      handleExpand(expanded, record) {
        // this.expandedRowKeys = []
        this.expandedRowKeys.splice(0)
        if (expanded === true) {
          this.expandedRowKeys.push(record.id)
        }
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
  }
</style>