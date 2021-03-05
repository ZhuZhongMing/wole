<template>
  <a-card :bordered="false">
    <a-steps class="steps" :current="currentTab">
      <!--<a-step title="客户信息" />-->
      <a-step title="订单信息" />
      <a-step title="生产计划" />
      <a-step title="完成" />
    </a-steps>
    <div class="content">
      <!--<MbpCustomerForm v-if="currentTab === 0" ref="MbpCustomerForm"  @nextStep="nextStep"/>-->
      <MbpOrderModal v-if="currentTab === 0" ref="MbpOrderModal" @nextStep="nextStep" @prevStep="prevStep"/>
      <MbpMainplanabstractModal v-if="currentTab === 1" ref="MbpMainplanabstractModal" @nextStep="nextStep" @prevStep="prevStep"></MbpMainplanabstractModal>
      <step3 v-if="currentTab === 2" ref="step3" @finish="finish"/>
    </div>
  </a-card>
</template>

<script>
  import MbpCustomerForm from './form/MbpCustomerForm'
  import MbpOrderModal from './form/MbpOrderModal'
  import MbpMainplanabstractModal from './form/MbpMainplanabstractModal'
  import Step3 from './form/Step3'

  export default {
    name: "StepForm",
    components: {
      MbpCustomerForm,
      MbpOrderModal,
      MbpMainplanabstractModal,
      Step3
    },
    data () {
      return {
        description: '将一个冗长或用户不熟悉的表单任务分成多个步骤，指导用户完成。',
        currentTab: 0,

        // form
        form: null,
        /*客户编号*/
        customerId: "",
        /*订单编号*/
        orderId: "",
        /*生产计划编号*/
        planId: ""
      }
    },
    methods: {
      // handler
      nextStep (model) {
        //console.log("model : " + model.id)
        //console.log("model : " + model.customerName)
        if (this.currentTab < 3) {
          this.currentTab += 1
          /*转至订单页面，需生成订单ID*/
          if (this.currentTab == 0) {
            this.$nextTick(function(){
              /*生成订单编号*/
              if (this.orderId) {
                this.$refs.MbpOrderModal.loadForm(this.orderId)
              } else {
                this.$refs.MbpOrderModal.genOrderId()
              }
              /*将客户id传至订单form*/
              this.$refs.MbpOrderModal.getCustomerId(model.id, model.customerName)
            })
          } else if (this.currentTab == 1) { // 跳转至生产计划页面，将订单号传过去
            this.$nextTick(function(){
              /*将订单编号，标题传至生产计划form*/
              this.$refs.MbpMainplanabstractModal.getOrderId(model.id, model.orderTitle)
            })
          }
        }
      },
      prevStep (model) {
        if (this.currentTab > 0) {
          this.currentTab -= 1
          /*if (this.currentTab == 0) {
            /!*客户编号*!/
            if (model.customerId) {
              this.customerId = model.customerId
            }
            /!*订单编号*!/
            if (model.id) {
              this.orderId = model.id
            }
            this.$nextTick(function(){
              this.$refs.MbpCustomerForm.loadForm(model.customerId)
            })
          } else*/ if (this.currentTab == 0) {
            /*订单编号*/
            if (model.orderId) {
              this.orderId = model.orderId
            }
            /*计划编号*/
            if (model.id) {
              this.planId = model.id
            }
            this.$nextTick(function(){
              this.$refs.MbpOrderModal.loadForm(model.orderId)
            })
          }
        }
      },
      finish () {
        this.currentTab = 0
        this.customerId = ""
        this.orderId = ""
        this.planId = ""
      }
    }
  }
</script>

<style lang="less" scoped>
  .steps {
    max-width: 1600px;
    width: 80%;
    margin: 16px auto;
  }
  .content {
    max-width: 1600px;
    width: 80%;
    margin: 16px auto;
  }
</style>