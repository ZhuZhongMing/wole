<template>
  <div class="top-left-cmp">
    <div class="dc-left">
      <dv-border-box-5>
        <div class="main-value"><span v-text="vo.dsum"></span>件</div>
        <div class="compare-value"><span>本月</span><span v-text="vo.msum"></span></div>
        <!--<div class="compare-value"><span>环比</span>15</div>-->
      </dv-border-box-5>
      <div class="dc-text">
        警报次数
        <dv-decoration-3 style="width:200px;height:20px;" />
      </div>
    </div>
    <div class="dc-right">
      <div class="dc-text">
        生产数量
        <dv-decoration-3 style="width:200px;height:20px;" />
      </div>
      <dv-border-box-5 :reverse="true">
        <div class="main-value"><span v-text="alarm.dsum"></span>次</div>
        <div class="compare-value"><span>累计</span><span v-text="alarm.msum"></span></div>
        <!--<div class="compare-value"><span>环比</span>9</div>-->
      </dv-border-box-5>
    </div>
  </div>
</template>

<script>
import { getRequest } from '../../util/request'
export default {
  name: 'TopLeftCmp',
  data () {
    return {
      cncmodel: {},
      vo: {},
      alarm: {},
      url: {
        sumByEquipment: '/system/dailyCapacity/sumByEquipment',
        countAlarmByEquipment: '/mqtt/cncModel/coutAlarm'
      }
    }
  },
  methods: {
    getCNCModel5 (cncmodel) {
      this.cncmodel = cncmodel
      this.sumByEquipment()
      this.countAlarmByEquipment()
    },
    sumByEquipment () {
      if (this.cncmodel.cncsn) {
        var parm = {
          equipmentsn: this.cncmodel.cncsn
        }
        getRequest(this.url.sumByEquipment, parm).then(res => {
          if (res.data.result) {
            this.vo = res.data.result
          }
        }).catch(exc => {
          console.log('查询设备今日产量！异常信息：' + exc)
        })
      }
    },
    countAlarmByEquipment () {
      if (this.cncmodel.cncsn) {
        getRequest(this.url.countAlarmByEquipment, this.cncmodel).then(res => {
          if (res.data.result) {
            this.alarm = res.data.result
          }
        }).catch(exc => {
          console.log('查询设备警报次数！异常信息：' + exc)
        })
      }
    }
  }
}
</script>

<style lang="less">
.top-left-cmp {
  display: flex;

  .dc-left, .dc-right {
    width: 50%;
  }

  .dv-border-box-5 {
    height: 60%;
  }

  .dc-text {
    display: flex;
    flex-direction: column;
    height: 40%;
    font-size: 20px;
    padding: 20px;
    box-sizing: border-box;
  }

  .dc-left .dc-text {
    align-items: flex-end;
    justify-content: center;
  }

  .dc-right .dc-text {
    justify-content: flex-start;
    padding-top: 20px;
  }

  .dc-left .dv-border-box-5 {
    padding: 30px;
    box-sizing: border-box;
  }

  .dc-right .dv-border-box-5 {
    padding: 40px;
    padding-left: 75px;
    box-sizing: border-box;
  }

  .main-value {
    font-weight: bold;
    font-size: 30px;

    span {
      font-size: 50px;
      color: #00c0ff;
      margin-right: 15px;
    }
  }

  .compare-value {
    height: 35px;
    line-height: 35px;
    font-size: 18px;

    span {
      margin-right: 30px;
    }
  }
}
</style>
