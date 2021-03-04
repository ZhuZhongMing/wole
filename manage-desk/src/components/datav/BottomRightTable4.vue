<template>
  <div class="bottom-right-table-4">
    <dv-border-box-6>
      <div class="table-name">
        <img src="./img/icon4.png" />设备报警信息
      </div>

      <dv-scroll-board :config="config" />
    </dv-border-box-6>
  </div>
</template>

<script>
import { getRequest } from '../../util/request'
export default {
  name: 'BottomRightTable4',
  data () {
    return {
      config: {
        header: ['报警号码', '报警信息', '报警时间'],
        data: [],
        index: false,
        columnWidth: [80, 100, 150],
        align: ['left', 'left', 'left'],
        headerBGC: '#0a3861',
        oddRowBGC: 'rgba(9, 37, 50, 0.4)',
        evenRowBGC: 'rgba(10, 32, 50, 0.3)'
      },
      cncmodel: {},
      url: {
        queryAlarmByEId: '/mqtt/cncModel/queryAlarmByEId'
      }
    }
  },
  methods: {
    getCNCModel4 (cncmodel) {
      this.cncmodel = cncmodel
      this.queryAlarmByEId()
    },
    queryAlarmByEId () {
      if (this.cncmodel.cncsn) {
        getRequest(this.url.queryAlarmByEId, this.cncmodel).then(res => {
          if (res.data.result) {
            var arr = []
            res.data.result.forEach((item, index) => {
              arr[index] = [item.alarmnum, item.alarminfo, item.datatime]
            })
            this.config = {
              header: ['报警号码', '报警信息', '报警时间'],
              data: arr,
              index: false,
              columnWidth: [80, 100, 150],
              align: ['left', 'left', 'left'],
              headerBGC: '#0a3861',
              oddRowBGC: 'rgba(9, 37, 50, 0.4)',
              evenRowBGC: 'rgba(10, 32, 50, 0.3)'
            }
          }
        }).catch(exc => {
          console.log('CNC查询计划详情！异常信息：' + exc)
        })
      }
    }
  }
}
</script>

<style lang="less">
.bottom-right-table-4 {
  width: calc(~"30% + 10px");
  //width: calc(~"25% + 10px");
  height: 100%;
  margin: 0 -5px;

  .border-box-content {
    padding: 20px;
    box-sizing: border-box;
  }

  .table-name {
    height: 45px;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 15px;

    img {
      width: 40px;
      height: 40px;
      margin-right: 5px;
    }
  }

  .dv-scroll-board {
    height: calc(~"100% - 60px");
  }
}
</style>
