<template>
  <div class="bottom-left-chart-1">

    <div class="header-name">机床设备详情</div>
    <!--<div class="details-value"><span>99.01</span>%</div>
    <dv-charts :option="option" />-->
    <div style="width:90%;height:2px; margin: 0px auto; background-color: gray;"></div>
    <div class="program_name">
      <p style="display: flex;">
        <span style="flex: 2; text-align: center;">程序名称:</span>
        <span style="flex: 3;" v-text="cncmodel.programname"></span>
      </p>
      <p style="display: flex;">
        <span style="flex: 2; text-align: center;">&nbsp;&nbsp;&nbsp;&nbsp;机床ip:</span>
        <span style="flex: 3;" v-text="cncmodel.cncip"></span>
      </p>
      <p style="display: flex;">
        <span style="flex: 2; text-align: center;">机床模式:</span>
        <span style="flex: 3;" v-text="cncmodel.cncmodel"></span>
      </p>
      <p style="display: flex;">
        <span style="flex: 2; text-align: center;">机床状态:</span>
        <span style="flex: 3;" v-text="cncmodel.cncstate"></span>
      </p>
      <p style="display: flex;">
        <span style="flex: 2; text-align: center;">通电时间:</span>
        <span style="flex: 3;" v-text="cncmodel.powerntime"></span>
      </p>
      <p style="display: flex;">
        <span style="flex: 2; text-align: center;">运行时间:</span>
        <span style="flex: 3;" v-text="cncmodel.runningtime"></span>
      </p>
      <p style="display: flex;">
        <span style="flex: 2; text-align: center;">加工时间:</span>
        <span style="flex: 3;" v-text="cncmodel.cuttingtime"></span>
      </p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'BottomLeftChart1',
  data () {
    return {
      option: {
        series: [
          {
            type: 'pie',
            data: [
              { name: '监控系统', value: 93, radius: ['30%', '33%'] },
              { name: '收费系统', value: 65, radius: ['32%', '35%'] },
              { name: '通信系统', value: 32, radius: ['31%', '37%'] },
              { name: '供配电系统', value: 44, radius: ['31%', '32%'] },
              { name: '其它', value: 52, radius: ['32%', '35%'] }
            ],
            outsideLabel: {
              labelLineEndLength: 20,
              formatter: '{percent}%\n{name}',
              style: {
                fill: '#fff'
              }
            }
          },
          {
            name: 'decoration ring',
            type: 'pie',
            data: [
              { value: 10, radius: ['50%', '53%'] }
            ],
            outsideLabel: {
              show: false
            },
            pieStyle: {
              fill: 'rgba(255, 255, 255, 0.2)'
            }
          },
          {
            name: 'decoration ring',
            type: 'pie',
            data: [
              { value: 10, radius: ['45%', '46%'] }
            ],
            outsideLabel: {
              show: false
            },
            pieStyle: {
              fill: 'rgba(255, 255, 255, 0.2)'
            }
          }
        ],
        color: ['#00c0ff', '#3de7c9', '#fff', '#00c0ff', '#3de7c9', '#fff']
      },
      cncmodel: {}
    }
  },
  methods: {
    getCNCModel1 (cncmodel) {
      this.cncmodel = cncmodel
    }
  }
}
</script>

<style lang="less">
.bottom-left-chart-1 {
  position: relative;

  .program_name{
    /*border-radius: 10px;
    border: 1px rgba(1, 153, 209, .5) solid;*/
    width: 90%;
    height: 70%;//height: 45%; 修改过的
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

  .decoration-ring {
    position: absolute;
    width: 200px;
    height: 200px;
    border-radius: 50%;
    border: 5px solid fade(#fefefe, 30);
    top: 190px;
    left: 50%;
    transform: translateX(-50%);
  }
}
</style>
