<template>
  <a-table
    rowKey="id"
    size="middle"
    bordered
    :loading="loading"
    :columns="columns"
    :dataSource="dataSource"
    :pagination="false"
  >

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

    <span slot="action" slot-scope="text, record">
      <a @click="handleOKStart(record)" v-if="record.planstatusId == 0 || record.planstatusId == '0'">确认开始</a>
    </span>

  </a-table>
</template>

<script>
  import { getAction,putAction } from '@api/manage'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: 'MbpMainplanSubTable',
    mixins: [JeecgListMixin],
    props: {
      record: {
        type: Object,
        default: null,
      }
    },
    data() {
      return {
        description: '生产计划明细内嵌列表',
        disableMixinCreated: true,
        loading: false,
        dataSource: [],
        columns: [
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
            title: '开始时间',
            align: 'center',
            dataIndex: 'startTime',
          },
          {
            title: '完成时间',
            align: 'center',
            dataIndex: 'finishTime',
          },
          {
            title: '计划类型编号',
            align: 'center',
            dataIndex: 'planTypeId',
          },
          {
            title: '客户名称',
            align: 'center',
            dataIndex: 'customenrName',
          },
          {
            title: '加工顺序',
            align: 'center',
            dataIndex: 'processOrder',
          },
          {
            title: '版本号',
            align: 'center',
            dataIndex: 'versionNo',
          },*/
          {
            title: '图号',
            align: 'center',
            dataIndex: 'figureNo',
          },
          {
            title: '设备编号',
            align: 'center',
            dataIndex: 'equipmentId_dictText'
          },
          /*{
            title: '优先级编号',
            align: 'center',
            dataIndex: 'priorityId',
          },*/
          /*{
            title: '是否关闭',
            align: 'center',
            dataIndex: 'isClosed',
          },*/
          {
            title: '备注',
            align: 'center',
            dataIndex: 'disp',
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
          /*{
            title: '创建人',
            align: 'center',
            dataIndex: 'createBy',
          },
          {
            title: '创建时间',
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
          {
            title: '删除标识0-正常,1-已删除',
            align: 'center',
            dataIndex: 'delFlag_dictText'
          },*/
        ],
        url: {
          listByMainId: '/system/mbpMainplanabstract/queryMbpMainplanByMainId',
          handleOKStart: '/system/mbpMainplanabstract/handleOKStart'
        },
      }
    },
    watch: {
      record: {
        immediate: true,
        handler() {
          if (this.record != null) {
            this.loadData(this.record)
          }
        }
      }
    },
    methods: {

      loadData(record) {
        // console.log("加载数据 ： " + JSON.stringify(record))
        this.loading = true
        this.dataSource = []
        getAction(this.url.listByMainId, {
          id: record.id
        }).then((res) => {
          if (res.success) {
            this.dataSource = res.result.records
          }
        }).finally(() => {
          this.loading = false
        })
      },
      /*点击确认开始*/
      handleOKStart(record) {
        console.log("确认开始 ： " + JSON.stringify(record))

        this.loading = true
        putAction(this.url.handleOKStart, {
          id: record.id,
          equipmentId: record.equipmentId
        }).then((res) => {
          if (res.success) {
            //this.dataSource = res.result.records
            /*成功*/
            this.$message.success(res.message)
            this.loadData({id: record.mainplanAbstractId})
          } else {
            /*失败*/
            this.$message.error(res.message)
            this.loading = false
          }
        })

      }
    },
  }
</script>

<style scoped>
</style>
