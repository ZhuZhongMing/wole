<template>
  <div id="data-view">
    <dv-full-screen-container>
      <div class="main-header">
        <div class="mh-left" style="margin-left: 20px"><a @click="handelEquipmentList">设备列表</a>
          <span style="margin-left: 50px; color: white;">当前设备：</span><a style="color: #00c0ff; font-size: 16px;" v-text="equipment.equipmentName"></a>
        </div>
        <div class="mh-middle">监控中心</div>
        <div class="mh-right">
          <dv-border-box-2
            style=" height: 50px; line-height: 50px; text-align:center; margin-right: 10px;"
          >
            <span>当前时间：</span><span v-text="nowTime"></span>
          </dv-border-box-2>
        </div>
      </div>
      <dv-border-box-1 class="main-container">
        <div class="mc-top">
          <Top-Left-Cmp ref="topLeftCmp"/>
          <Top-Middle-Cmp />
          <Top-right-Cmp ref="topRightCmp"/>
        </div>
        <div class="mc-bottom">
          <dv-border-box-6 class="bottom-left-container">
            <dv-decoration-4 class="mcb-decoration-1" style="width:5px;height:45%;" />
            <dv-decoration-4 class="mcb-decoration-2" style="width:5px;height:45%;" />
            <Bottom-Left-Chart-1 ref="bLeftChart1"/>
            <Bottom-Left-Chart-2 ref="bLeftChart2"/>
          </dv-border-box-6>

          <div class="bottom-right-container">
            <Bottom-Right-Table-1 />
            <!--<Bottom-Right-Table-2 />-->
            <Bottom-Right-Table-3  ref="bRightTable3"/>
            <Bottom-Right-Table-4 ref="bRightTable4"/>
          </div>
        </div>
      </dv-border-box-1>

    </dv-full-screen-container>
    <a-modal
      title=""
      :visible="visible"
      :confirm-loading="confirmLoading"
      :footer="null"
      :maskClosable="false"
      @ok="handleOk"
      @cancel="handleCancel"
      :width="800"
      :bodyStyle="{opacity:0.8, color: 'white',borderRadius: '5px', fontSize:'18px', backgroundColor: '#23509a', border: '2px solid', borderColor: '#00c0ff', height: '600px'}"
      :maskStyle="{}"
      :mask="true"
    >
      <table id="etable" style="width: 80%; margin: 0px auto; margin-top: 30px; text-align: center;">
        <tr style="border: 1px white solid; background-color: #00c0ff;">
          <td style="border: 1px white solid; line-height: 40px; height: 40px;">设备ID</td>
          <td style="border: 1px white solid; line-height: 40px; height: 40px;">设备名称</td>
          <td style="border: 1px white solid; line-height: 40px; height: 40px;">设备状态</td>
          <td style="border: 1px white solid; line-height: 40px; height: 40px;">操作</td>
        </tr>
        <tr style="border: 1px white solid;" v-for="(item, key) in equipmentList" :key="key">
          <td style="border: 1px white solid; line-height: 35px; height: 35px;" v-text="item.equipmentId"></td>
          <td style="border: 1px white solid; line-height: 35px; height: 35px;" v-text="item.equipmentName"></td>
          <td style="border: 1px white solid; line-height: 35px; height: 35px;" v-if="item.equipmentStatus==1">在线</td>
          <td style="border: 1px white solid; line-height: 35px; height: 35px;" v-if="item.equipmentStatus==0">离线</td>
          <td style="border: 1px white solid; line-height: 35px; height: 35px;"><a-button type="primary" @click="handleQuery(item)">查看</a-button></td>
        </tr>
      </table>
    </a-modal>
  </div>
</template>

<script>
import TopLeftCmp from './TopLeftCmp'
import TopMiddleCmp from './TopMiddleCmp'
import TopRightCmp from './TopRightCmp'

import BottomLeftChart1 from './BottomLeftChart1'
import BottomLeftChart2 from './BottomLeftChart2'

import BottomRightTable1 from './BottomRightTable1'
// import BottomRightTable2 from './BottomRightTable2'
import BottomRightTable3 from './BottomRightTable3'
import BottomRightTable4 from './BottomRightTable4'
import { getRequest } from '../../util/request'

export default {
  name: 'DataView',
  components: {
    TopLeftCmp,
    TopMiddleCmp,
    TopRightCmp,
    BottomLeftChart1,
    BottomLeftChart2,
    BottomRightTable1,
    /* BottomRightTable2, */
    BottomRightTable3,
    BottomRightTable4
  },
  data () {
    return {
      nowTime: '',
      ModalText: 'Content of the modal',
      visible: false,
      confirmLoading: false,
      equipmentList: [],
      equipment: '',
      cncmodel: {},
      url: {
        getEquipmentList: '/mqtt/mbpDevEquipment/getAll',
        getEquipmentInfoById: '/mqtt/cncModel/queryByEquipmentId'
      }
    }
  },
  methods: {
    handleQuery (item) {
      // eslint-disable-next-line eqeqeq
      if (item.equipmentStatus == 0) {
        this.$message.warning('当前设备不在线')
        return
      }
      // this.$message.warning('操作成功')
      this.equipment = item
      this.getEquipmentInfoById(this.equipment.equipmentId)
      this.handleCancel()
    },
    getEquipmentList () {
      getRequest(this.url.getEquipmentList, null).then(res => {
        this.equipmentList = res.data.result
        // this.$emit('changeMessageCount')
        // this.$emit('getEquipmentId', this.equipmentList[0]) // 第一台设备暂无数据
        this.equipmentList.forEach((item, i) => {
          // eslint-disable-next-line eqeqeq
          if (item.equipmentStatus == 1) {
            this.equipment = item
          }
        })
        this.getEquipmentInfoById(this.equipment.equipmentId)
      }).catch(exc => {
        console.log('查询设备列表发生异常！异常信息：' + exc)
      })
    },
    handelEquipmentList () {
      // this.getEquipmentList()
      this.visible = true
    },
    handleOk (e) {
      this.ModalText = 'The modal will be closed after two seconds'
      this.confirmLoading = true
      setTimeout(() => {
        this.visible = false
        this.confirmLoading = false
      }, 2000)
    },
    handleCancel (e) {
      console.log('Clicked cancel button')
      this.visible = false
    },
    getNowTime () {
      var date = new Date()
      var year = date.getFullYear()
      var month = date.getMonth() + 1
      var day = date.getDate()
      var hour = date.getHours()
      var minutes = date.getMinutes()
      var second = date.getSeconds()
      this.nowTime = year + '年' + month + '月' + day + '日 ' + hour + '时' + minutes + '分' + second + '秒'
    },
    getEquipmentInfoById: function (id) {
      getRequest(this.url.getEquipmentInfoById, { cncsn: id }).then(res => {
        this.cncmodel = res.data.result
        this.$refs.bLeftChart1.getCNCModel1(this.cncmodel)
        this.$refs.bRightTable3.getCNCModel2(this.cncmodel)
        this.$refs.bLeftChart2.getCNCModel3(this.cncmodel)
        this.$refs.bRightTable4.getCNCModel4(this.cncmodel)
        this.$refs.topLeftCmp.getCNCModel5(this.cncmodel)
        this.$refs.topRightCmp.getCNCModel6(this.cncmodel)
        if (this.cncmodel.alarmnum != 0) {
          this.$notification.warning({
            message: '警报提示',
            description: this.cncmodel.alarminfo
          })
        }
      }).catch(exc => {
        console.log('查询设备列表发生异常！异常信息：' + exc)
      })
    }
  },
  mounted () {
    this.getNowTime()
    this.getEquipmentList()
    setInterval(() => {
      this.getNowTime()
    }, 1000)
  }
}
</script>

<style lang="less">
#data-view {
  width: 100%;
  height: 100%;
  background-color: #030409;
  color: #fff;

  #dv-full-screen-container {
    background-image: url('./img/bg.png');
    background-size: 100% 100%;
    box-shadow: 0 0 3px blue;
    display: flex;
    flex-direction: column;
  }
  .main-header {
    height: 80px;
    display: flex;
    justify-content: space-between;
    align-items: flex-end;

    .mh-left {
      font-size: 20px;
      color: rgb(1,134,187);

      a:visited {
        color: rgb(1,134,187);
      }
    }

    .mh-middle {
      font-size: 30px;
    }

    .mh-left, .mh-right {
      width: 450px;
    }
  }

  .main-container {
    height: calc(~"100% - 80px");

    .mc-top, .mc-bottom {
      box-sizing: border-box;
      padding: 30px;
      display: flex;
    }

    .mc-top {
      height: 40%;
    }

    .mc-bottom {
      height: 60%;
    }

    .top-left-cmp, .bottom-left-container {
      width: 32%;
    }

    .top-middle-cmp, .top-right-cmp {
      width: 34%;
    }

    .bottom-left-container {
      position: relative;

      .border-box-content {
        display: flex;
      }

      .mcb-decoration-1, .mcb-decoration-2 {
        position: absolute;
        left: 50%;
        margin-left: -2px;
      }

      .mcb-decoration-1 {
        top: 5%;
        transform: rotate(180deg);
      }

      .mcb-decoration-2 {
        top: 50%;
      }

      .bottom-left-chart-1, .bottom-left-chart-2 {
        width: 50%;
        height: 100%;
      }
    }

    .bottom-right-container {
      width: 68%;
      padding-left: 30px;
      box-sizing: border-box;
      display: flex;
    }
  }
}
</style>
