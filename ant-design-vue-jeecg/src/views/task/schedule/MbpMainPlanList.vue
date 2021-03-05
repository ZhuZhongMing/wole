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
    <!--<div class="table-operator">
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
    </div>-->

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <!--<i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>-->
        <a style="margin-left: 24px">&nbsp;</a>
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
        class="j-table-force-nowrap"
        @change="handleTableChange">
        <!--:rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"-->

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
          <a @click="handlLook(record)">查看进度</a>
        </span>

      </a-table>
    </div>
    <!--计划进度-->
    <a-modal
      title="生产进度"
      :visible="modelVisible"
      @cancel="handleCancelModel"
      @ok="handleCancelModel"
    >
      <div style="margin-left: 20%;">
        <a-timeline>
          <a-timeline-item v-for="(item,key) in modelData.list" :key="key" :color="item.color">{{item.workprocessName}}</a-timeline-item>
        </a-timeline>
      </div>
    </a-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { loadCategoryData } from '@/api/api'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import Area from '@/components/_util/Area'
  import { getAction } from '@/api/manage'

  export default {
    name: 'MbpMainPlanList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
    },
    data () {
      return {
        modelVisible: false,
        modelData: {},
        description: '计划详情进度页面',
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
            title: '订单标题',
            align: 'center',
            dataIndex: 'orderTitle'
          },
          {
            title: '产品名称',
            align: 'center',
            dataIndex: 'materialId_dictText'
          },
          {
            title: '数量',
            align: 'center',
            dataIndex: 'number',
          },
          {
            title: '已完成',
            align: 'center',
            dataIndex: 'finishNumber',
          },
          /*{
            title: '图号',
            align: 'center',
            dataIndex: 'figureNo',
          },*/
          {
            title: '设备编号',
            align: 'center',
            dataIndex: 'equipmentId_dictText'
          },
          {
            title: '备注',
            align: 'center',
            dataIndex: 'disp',
          },
          {
            title: '创建时间',
            align: 'center',
            dataIndex: 'createTime',
          },
          {
            title: '状态', // 计划状态编号0-等待确认,1-正在进行,2-计划完成
            align: 'center',
            dataIndex: 'planstatusId',
            customRender:(text)=>{
              if(text == '' && text == null){
                return ''
              }else{
                if (text == 0 || text == '0') {
                  return <span style="color:#F5222D;">等待确认</span>
                } else if (text == 1 || text == '1') {
                  return <span style="color:#52C41A;">正在生产</span>
                } else if (text == 2 || text == '2') {
                  return <span style="color:#722ED1;">完成生产</span>
                }
              }
            }
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
            /*fixed: 'right',*/
            width: 100
          }
        ],
        url: {
          list: "/system/mbpMainplanabstract/listPlan",
          lookSchedule: '/system/mbpMainplanabstract/lookSchedule'
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
      handleCancelModel(e) {
        this.modelVisible = false
      },
      lookSchedule(record) {
        var param = {
          equipmentId: record.equipmentId, //设备编号
          materialId: record.materialId //产品编号
        }
        //this.loading = true
        getAction(this.url.lookSchedule, param).then((res) => {
          if (res.success) {
            //this.dataSource = res.result.records
            /!*成功*!/
            this.modelData = res.result
            this.modelVisible = true
            //console.log("查看进度 ： " + JSON.stringify(res.result))
          } else {
            /!*失败*!/
            this.$message.error("查看失败")
            //this.loading = false
          }
        })

      },
      /*点击查看进度*/
      handlLook(record) {
        if (record.planstatusId == 0 ||  record.planstatusId == '0') {
          this.$message.warning('此计划暂未开始')
        } else if (record.planstatusId == 1 ||  record.planstatusId == '1') {
          //this.$emit('lookSchedule',record)
          this.lookSchedule(record)
        } else if (record.planstatusId == 2 ||  record.planstatusId == '2') {
          this.$message.success('此计划已完成')
        }
      },
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