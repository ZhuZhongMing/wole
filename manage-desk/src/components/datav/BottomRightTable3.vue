<template>
  <div class="bottom-right-table-3">
    <dv-border-box-6>
      <div class="table-name">
        <img src="./img/icon3.png" />设备状态
      </div>
      <div class="row">
        <div>
          <p style="margin-top: 20px">
            <span>主轴倍率</span><span v-text="cncmodel.spindlerate"></span>
          </p>
          <p>
            <span>主轴转数</span><span v-text="cncmodel.spindlespeed"></span>
          </p>
          <p>
            <span>主轴负载</span><span v-text="cncmodel.spindleload"></span>
          </p>
        </div>
        <div>
          <p style="margin-top: 20px">
            <span>进给轴转速</span><span v-text="cncmodel.feedspeed"></span>
          </p>
          <p>
            <span>进给轴倍率</span><span v-text="cncmodel.feedrate"></span>
          </p>
          <p>
            <span>进给轴设定转速</span><span v-text="cncmodel.feedspeedset"></span>
          </p>
        </div>
      </div>
      <div class="row">
        <div>
          <p style="margin-top: 20px">
            <span>X轴负载</span><span v-text="cncmodel.xload"></span>
          </p>
          <p>
            <span>Y轴负载</span><span v-text="cncmodel.yload"></span>
          </p>
          <p>
            <span>Z轴负载</span><span v-text="cncmodel.zload"></span>
          </p>
        </div>
        <div>
          <p style="margin-top: 20px">
            <span>A轴负载</span><span v-text="cncmodel.aload"></span>
          </p>
          <p>
            <span>B轴负载</span><span v-text="cncmodel.bload"></span>
          </p>
          <p>
            <span>C轴负载</span><span v-text="cncmodel.cload"></span>
          </p>
        </div>
      </div>
      <!--<dv-scroll-board :config="config" />-->
    </dv-border-box-6>
  </div>
</template>

<script>
export default {
  name: 'BottomRightTable3',
  data () {
    return {
      config: {
        data: [
          ['栏杆机不抬杆', '月累计：4起'],
          ['栏杆机落杆', '月累计：3起'],
          ['光端机故障', '月累计：3起'],
          ['票据打印机', '月累计：3起'],
          ['视频花屏', '月累计：2起'],
          ['车牌识别仪', '月累计：1起']
        ],
        index: true,
        columnWidth: [30, 100],
        align: ['center'],
        oddRowBGC: 'rgba(9, 37, 50, 0.4)',
        evenRowBGC: 'rgba(10, 32, 50, 0.3)'
      },
      cncmodel: {}
    }
  },
  methods: {
    getCNCModel2 (cncmodel) {
      this.cncmodel = cncmodel
    }
  }
}
</script>

<style lang="less">
.bottom-right-table-3 {
  width: calc(~"40% + 10px");
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

  span:first-child {
    text-align: right;
    width: 55%;
    /*padding-left: 5%;
    padding-right: 10%;*/
  }
  span:last-child {
    text-align: center;
    width: 45%;
  }
  span{
    /*padding-left: 10%;*/
    font-size: 20px;
    display: inline-block;
  };
  .row {
    margin: 0 auto;
    border: 1px solid rgba(1, 153, 209, .5);
    width: 95%;
    height: 40%;
    display: flex;
    span:first-child {
      flex: 1;
      color: rgba(2, 154, 215, .9);
    }
    span {
      font-size: 16px;
      flex: 2;
    }
  }
  .row:first-child{
    border-bottom: none;
  }
  .row div {
    border: 1px solid rgba(1, 153, 209, .5);
    width: 100%;
    height: 100%;
  }
  .row div p:first-child {
    margin-top: 50%;
  }
}
</style>
