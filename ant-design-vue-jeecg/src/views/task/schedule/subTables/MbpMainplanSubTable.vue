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
      <a @click="handlLook(record)">查看进度</a>
    </span>

  </a-table>
</template>

<script>
  import { getAction } from '@api/manage'
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
        ],
        url: {
          listByMainId: '/system/mbpMainplanabstract/queryMbpMainplanByMainId',
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
          id: record.id,
          planstatusId: 1
        }).then((res) => {
          if (res.success) {
            this.dataSource = res.result.records
          }
        }).finally(() => {
          this.loading = false
        })
      },
      /*点击查看进度*/
      handlLook(record) {
        if (record.planstatusId == 0 ||  record.planstatusId == '0') {
          this.$message.warning('此计划暂未开始')
        } else if (record.planstatusId == 1 ||  record.planstatusId == '1') {
          this.$emit('lookSchedule',record)
        } else if (record.planstatusId == 2 ||  record.planstatusId == '2') {
          this.$message.success('此计划已完成')
        }
      },
    },
  }
</script>

<style scoped>
</style>
