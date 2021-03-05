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
      <a-button type="primary" icon="download" @click="handleExportXls('cnc数据')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>
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

    <cnc-model-modal ref="modalForm" @ok="modalFormOk"></cnc-model-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import CncModelModal from './modules/CncModelModal'
  import JSuperQuery from '@/components/jeecg/JSuperQuery.vue'

  export default {
    name: 'CncModelList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      CncModelModal,
      JSuperQuery,
    },
    data () {
      return {
        description: 'cnc数据管理页面',
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
          /*{
            title:'客户名称',
            align:"center",
            dataIndex: 'customername'
          },
          {
            title:'客户编号',
            align:"center",
            dataIndex: 'customerid'
          },*/
          /*{
            title:'机床名称',
            align:"center",
            dataIndex: 'cncname'
          },*/
          {
            title:'机床编号',
            align:"center",
            dataIndex: 'cncsn'
          },
/*          {
            title:'机床类型',
            align:"center",
            dataIndex: 'cnctype'
          },
          {
            title:'机床ip',
            align:"center",
            dataIndex: 'cncip'
          },
          {
            title:'机床模式',
            align:"center",
            dataIndex: 'cncmodel'
          },
          {
            title:'机床状态',
            align:"center",
            dataIndex: 'cncstate'
          },
          {
            title:'通电时间',
            align:"center",
            dataIndex: 'powerntime'
          },
          {
            title:'运行时间',
            align:"center",
            dataIndex: 'runningtime'
          },
          {
            title:'加工时间',
            align:"center",
            dataIndex: 'cuttingtime'
          },
          {
            title:'当前加工程序名',
            align:"center",
            dataIndex: 'programname'
          },{
            title:'目标加工数量',
            align:"center",
            dataIndex: 'targetcount'
          },
          {
            title:'主轴负载',
            align:"center",
            dataIndex: 'spindleload'
          },
          {
            title:'主轴转速',
            align:"center",
            dataIndex: 'spindlespeed'
          },
          {
            title:'主轴倍率',
            align:"center",
            dataIndex: 'spindlerate'
          },
          {
            title:'主轴设定转速',
            align:"center",
            dataIndex: 'spindlespeedset'
          },
          {
            title:'进给轴转速',
            align:"center",
            dataIndex: 'feedspeed'
          },
          {
            title:'进给轴倍率',
            align:"center",
            dataIndex: 'feedrate'
          },
          {
            title:'进给轴设定转速',
            align:"center",
            dataIndex: 'feedspeedset'
          },
          {
            title:'报警类型',
            align:"center",
            dataIndex: 'alarmtype'
          },
          {
            title:'报警号码',
            align:"center",
            dataIndex: 'alarmnum'
          },
          {
            title:'报警描述',
            align:"center",
            dataIndex: 'alarminfo'
          },*/
          {
            title:'刀具号',
            align:"center",
            dataIndex: 'toolnum'
          },
          {
            title:'X轴负载',
            align:"center",
            dataIndex: 'xload'
          },
          {
            title:'Y轴负载',
            align:"center",
            dataIndex: 'yload'
          },
          {
            title:'Z轴负载',
            align:"center",
            dataIndex: 'zload'
          },
          {
            title:'A轴负载',
            align:"center",
            dataIndex: 'aload'
          },
          {
            title:'B轴负载',
            align:"center",
            dataIndex: 'bload'
          },
          {
            title:'C轴负载',
            align:"center",
            dataIndex: 'cload'
          },
          /*{
            title:'IO采集功率',
            align:"center",
            dataIndex: 'iopower'
          },*/
          {
            title:'当前加工零件名',
            align:"center",
            dataIndex: 'partname'
          },
          {
            title:'已加工数量',
            align:"center",
            dataIndex: 'count'
          },
          {
            title:'采集时间',
            align:"center",
            dataIndex: 'datatime'
          },
          /*,
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }*/
          /*{
            title:'创建人',
            align:"center",
            dataIndex: 'createBy'
          },
          {
            title:'创建时间',
            align:"center",
            dataIndex: 'createTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'修改人',
            align:"center",
            dataIndex: 'updateBy'
          },
          {
            title:'修改时间',
            align:"center",
            dataIndex: 'updateTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'是否删除',
            align:"center",
            dataIndex: 'delFlag'
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
          list: "/mqtt/cncModel/list",
          delete: "/mqtt/cncModel/delete",
          deleteBatch: "/mqtt/cncModel/deleteBatch",
          exportXlsUrl: "/mqtt/cncModel/exportXls",
          importExcelUrl: "mqtt/cncModel/importExcel",

        },
        dictOptions:{},
        superFieldList:[],
      }
    },
    created() {
    this.getSuperFieldList();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'string',value:'customername',text:'客户名称',dictCode:''})
        fieldList.push({type:'string',value:'customerid',text:'客户变化',dictCode:''})
        fieldList.push({type:'string',value:'cncname',text:'机床名称',dictCode:''})
        fieldList.push({type:'string',value:'cncsn',text:'机床编号',dictCode:''})
        fieldList.push({type:'string',value:'cnctype',text:'机床类型',dictCode:''})
        fieldList.push({type:'string',value:'cncip',text:'机床ip',dictCode:''})
        fieldList.push({type:'string',value:'cncmodel',text:'机床模式',dictCode:''})
        fieldList.push({type:'string',value:'cncstate',text:'机床状态',dictCode:''})
        fieldList.push({type:'string',value:'powerntime',text:'通电时间',dictCode:''})
        fieldList.push({type:'string',value:'runningtime',text:'运行时间',dictCode:''})
        fieldList.push({type:'string',value:'cuttingtime',text:'加工时间',dictCode:''})
        fieldList.push({type:'string',value:'partname',text:'当前加工零件名',dictCode:''})
        fieldList.push({type:'string',value:'programname',text:'当前加工程序名',dictCode:''})
        fieldList.push({type:'int',value:'count',text:'已加工数量',dictCode:''})
        fieldList.push({type:'int',value:'targetcount',text:'目标加工数量',dictCode:''})
        fieldList.push({type:'double',value:'spindleload',text:'主轴负载',dictCode:''})
        fieldList.push({type:'double',value:'spindlespeed',text:'主轴转速',dictCode:''})
        fieldList.push({type:'double',value:'spindlerate',text:'主轴倍率',dictCode:''})
        fieldList.push({type:'double',value:'spindlespeedset',text:'主轴设定转速',dictCode:''})
        fieldList.push({type:'double',value:'feedspeed',text:'进给轴转速',dictCode:''})
        fieldList.push({type:'double',value:'feedrate',text:'进给轴倍率',dictCode:''})
        fieldList.push({type:'double',value:'feedspeedset',text:'进给轴设定转速',dictCode:''})
        fieldList.push({type:'string',value:'alarmtype',text:'报警类型',dictCode:''})
        fieldList.push({type:'string',value:'alarmnum',text:'报警号码',dictCode:''})
        fieldList.push({type:'string',value:'alarminfo',text:'报警描述',dictCode:''})
        fieldList.push({type:'int',value:'toolnum',text:'刀具号',dictCode:''})
        fieldList.push({type:'double',value:'xload',text:'X轴负载',dictCode:''})
        fieldList.push({type:'double',value:'yload',text:'Y轴负载',dictCode:''})
        fieldList.push({type:'double',value:'zload',text:'Z轴负载',dictCode:''})
        fieldList.push({type:'double',value:'aload',text:'A轴负载',dictCode:''})
        fieldList.push({type:'double',value:'bload',text:'B轴负载',dictCode:''})
        fieldList.push({type:'double',value:'cload',text:'C轴负载',dictCode:''})
        fieldList.push({type:'double',value:'iopower',text:'IO采集功率',dictCode:''})
        fieldList.push({type:'date',value:'datatime',text:'采集时间'})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>