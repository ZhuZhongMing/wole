<template>
  <div class="bottom-left-chart-2">
    <div class="header-name">设备生产任务</div>
    <div style="width:90%;height:2px; margin: 0px auto; background-color: gray;"></div>
    <!--<div class="details-value"><span>55.1</span>小时</div>
    <dv-charts :option="option" />-->
    <div v-if="plan.id != null" class="my-p">
      <p style="display: flex;">
        <span style="flex: 2; text-align: center;">产品名称:</span>
        <span style="flex: 3;" v-text="plan.materialName"></span>
      </p>
      <p style="display: flex;">
        <span style="flex: 2; text-align: center;">计划数量:</span>
        <span style="flex: 3;" v-text="plan.number"></span>
      </p>
      <p style="display: flex;">
        <span style="flex: 2; text-align: center;">完成数量:</span>
        <span style="flex: 3;" v-text="plan.finishNumber"></span>
      </p>
      <!--<p>
        <span>备注信息：</span><span></span>
      </p>-->
      <dv-water-level-pond :config="config" style="width:150px;height:200px; margin: auto; margin-top: 30px;" />
    </div>
    <div v-else class="my-p">
      <p style="margin-top: 40px; margin-left: 10px;font-size: 18px; color: #858f91">当前设备暂未指定生产计划</p>
    </div>
  </div>
</template>

<script>
import { getRequest } from '../../util/request'
export default {
  name: 'BottomLeftChart2',
  data () {
    return {
      config: {
        data: [0],
        shape: 'roundRect',
        waveHeight: 1
      },
      option: {
        series: [
          {
            type: 'gauge',
            startAngle: -Math.PI / 2,
            endAngle: Math.PI * 1.5,
            arcLineWidth: 7,
            data: [
              { name: '8小时以内', value: 25, gradient: ['#03c2fd', '#1ed3e5', '#2fded6'] },
              { name: '24小时以内', value: 45, gradient: ['#03c2fd', '#1ed3e5', '#2fded6'], radius: '52%' },
              { name: '48小时以内', value: 65, gradient: ['#03c2fd', '#1ed3e5', '#2fded6'], radius: '44%' },
              { name: '72小时以内', value: 35, gradient: ['#03c2fd', '#1ed3e5', '#2fded6'], radius: '36%' },
              { name: '大于72小时', value: 25, gradient: ['#03c2fd', '#1ed3e5', '#2fded6'], radius: '28%' }
            ],
            axisLabel: {
              show: false
            },
            axisTick: {
              show: false
            },
            pointer: {
              show: false
            },
            dataItemStyle: {
              lineCap: 'round'
            },
            backgroundArc: {
              show: false
            },
            details: {
              show: true,
              formatter: '{name}',
              position: 'start',
              offset: [-10, 0],
              style: {
                fill: '#fff',
                fontSize: 12,
                textAlign: 'right'
              }
            }
          }
        ]
      },
      cncmodel: {},
      plan: {},
      url: {
        getPlanInfo: '/system/mbpMainplanabstract/getByMainplan'
      }
    }
  },
  methods: {
    getCNCModel3 (cncmodel) {
      this.cncmodel = cncmodel
      this.getPlanInfo()
    },
    getPlanInfo () {
      if (this.cncmodel.cncsn) {
        let param = {
          equipmentId: this.cncmodel.cncsn,
          planstatusId: 1
        }
        getRequest(this.url.getPlanInfo, param).then(res => {
          if (res.data.result) {
            this.plan = res.data.result
            var real = (res.data.result.finishNumber / res.data.result.number) * 100
            this.config = {
              data: [real],
              shape: 'roundRect',
              waveHeight: 1
            }
          } else {
            this.plan = {}
            this.config = {
              data: [0],
              shape: 'roundRect',
              waveHeight: 1
            }
          }
        }).catch(exc => {
          console.log('CNC查询计划详情！异常信息：' + exc)
        })
      } else {
        this.plan = {}
      }
    }
  }
}
</script>

<style lang="less">
.bottom-left-chart-2 {
   .my-p{/*border-radius: 10px;
    border: 1px rgba(1, 153, 209, .5) solid;*/
    width: 90%;
    padding-left: 15px;
    padding-right: 15px;
  }
  .header-name {
    height: 150px;
    line-height: 150px;
    font-size: 20px;
    text-align: center;
  }

  .details-value {
    height: 40px;
    font-size: 30px;
    font-weight: bold;
    text-align: center;

    span {
      color: #00c0ff;
      font-size: 45px;
    }
  }

  .dv-charts-container {
    height: calc(~"100% - 190px");
  }
}
</style>
