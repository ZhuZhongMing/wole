<template>

    <a-spin :spinning="confirmLoading">
      <!--<a-spin :spinning="confirmLoading">-->
      <!-- 主表单区域 -->
      <a-form :form="form">
        <a-row>
          <a-col :xs="24" :sm="12">
            <a-form-item label="订单编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['orderId', validatorRules.orderId]" placeholder="请输入订单编号" :disabled="true"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="订单标题" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['orderTitle', validatorRules.orderTitle]" placeholder="请输入订单标题"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="客户" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['customerId', validatorRules.customerId]" :trigger-change="true" dictCode="mbp_customer,customer_name,id" placeholder="请选择客户"/>
              <!--<a-select v-decorator="['customerId', validatorRules.customerId]" placeholder="请选择客户">
                <a-option v-if="customer" :value="customer.id">{{customer.name}}</a-option>
              </a-select>-->
              <!--<a-input v-decorator="['customerName', validatorRules.customerName]" placeholder="请选择客户" disabled></a-input>-->
            </a-form-item>
          </a-col>
          <!--<a-col :xs="24" :sm="12">
            <a-form-item label="业务员" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-select-user-by-dep v-decorator="['salesman']"/>
            </a-form-item>
          </a-col>-->
          <!--<a-col :xs="24" :sm="12">
            <a-form-item label="部门编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-select-depart v-decorator="['deptId']" multi/>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="支付方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['paymentId']" placeholder="请输入支付方式"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="支付时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择支付时间" v-decorator="['paymentDate']" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="递送方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['deliveryMethod']" placeholder="请输入递送方式"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="递送费用" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['deliveryFee']" placeholder="请输入递送费用" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="审核人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['auditPerson']" placeholder="请输入审核人"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="审核时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择审核时间" v-decorator="['auditTime']" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="结算货币" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['currency']" placeholder="请输入结算货币"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="订单类别" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-category-select v-decorator="['orderType']" pcode="B07" placeholder="请选择订单类别" />
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="订单分配状态0-未全部分配,1-已全部分配" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['orderState']" :trigger-change="true" dictCode="order_state" placeholder="请选择订单分配状态0-未全部分配,1-已全部分配"/>
            </a-form-item>
          </a-col>-->
          <a-col :xs="24" :sm="12">
            <a-form-item label="合同编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['contract']" placeholder="请输入合同编号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="24">
            <a-form-item label="合同附件" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <j-upload v-decorator="['accessory']" :trigger-change="true"></j-upload>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="备注" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <a-textarea v-decorator="['disp']" rows="4" placeholder="请输入备注"/>
            </a-form-item>
          </a-col>
          <!--<a-col :xs="24" :sm="12">
            <a-form-item label="创建人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['createBy']" placeholder="请输入创建人"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="创建时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择创建时间" v-decorator="['createTime']" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="修改人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['updateBy']" placeholder="请输入修改人"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="修改时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择修改时间" v-decorator="['updateTime']" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="删除标识0-正常,1-已删除" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['delFlag']" :trigger-change="true" dictCode="del_flag" placeholder="请选择删除标识0-正常,1-已删除"/>
            </a-form-item>
          </a-col>-->

        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs" >
        <a-tab-pane tab="订单明细" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="mbpOrderlistTable.loading"
            :columns="mbpOrderlistTable.columns"
            :dataSource="mbpOrderlistTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>
        </a-tab-pane>

      </a-tabs>

      <a-row>
        <a-col :span="24" style="text-align: right">
          <a-form-item :wrapperCol="{span: 19, offset: 5}">
            <!--<a-button type="primary" @click="nextStep">提交</a-button>-->
            <a-button @click="prevStep">上一步</a-button>
            <a-button style="margin-left: 8px" type="primary" @click="handleOk">下一步</a-button>
          </a-form-item>
        </a-col>
      </a-row>

    </a-spin>

</template>

<script>

  import pick from 'lodash.pick'
  import { FormTypes,getRefPromise } from '@/utils/JEditableTableUtil'
  import { JEditableTableMixin } from '@/mixins/JEditableTableMixin'
  import { validateDuplicateValue } from '@/utils/util'
  import JDate from '@/components/jeecg/JDate'
  import JUpload from '@/components/jeecg/JUpload'
  import JSelectDepart from '@/components/jeecgbiz/JSelectDepart'
  import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JCategorySelect from '@/components/jeecg/JCategorySelect'
  import ACol from 'ant-design-vue/es/grid/Col'
  import { httpAction, getAction } from '@/api/manage'

  export default {
    name: 'MbpOrderModal',
    mixins: [JEditableTableMixin],
    components: {
      ACol,
      JDate,
      JUpload,
      JSelectDepart,
      JSelectUserByDep,
      JDictSelectTag,
      JCategorySelect,
    },
    data() {
      return {
        /*客户*/
        customer: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 6 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        labelCol2: {
          xs: { span: 24 },
          sm: { span: 3 },
        },
        wrapperCol2: {
          xs: { span: 24 },
          sm: { span: 20 },
        },
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 1,
        validatorRules: {
          orderId: {
            rules: [
              { required: true, message: '请输入订单编号!'},
            ]
          },
          orderTitle: {
            rules: [
              { required: true, message: '请输入订单标题!'},
            ]
          },
          customerId: {
            rules: [
              { required: true, message: '请输入客户编号!'},
            ]
          },
          customerName: {
            rules: [
              { required: true, message: '请输入客户编号!'},
            ]
          }
        },
        refKeys: ['mbpOrderlist', ],
        tableKeys:['mbpOrderlist', ],
        activeKey: 'mbpOrderlist',
        // 订单明细
        mbpOrderlistTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '产品编号',
              key: 'materialId',
              type: FormTypes.sel_search,
              dictCode:"mbp_product_map,product_name,id",
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: '数量',
              key: 'orderNum',
              type: FormTypes.inputNumber,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            /*{
              title: '模式',
              key: 'model',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '交货日期',
              key: 'finishDate',
              type: FormTypes.date,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },*/
            {
              title: '备注',
              key: 'disp',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            /*{
              title: '创建人',
              key: 'createBy',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '创建时间',
              key: 'createTime',
              type: FormTypes.date,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '修改人',
              key: 'updateBy',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '修改时间',
              key: 'updateTime',
              type: FormTypes.date,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '删除标识0-正常,1-已删除',
              key: 'delFlag',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '未分配数量',
              key: 'allotNumber',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              disabled: true
            },
            {
              title: '已分配数量',
              key: 'allotedNumber',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              disabled: true
            },*/
          ]
        },
        url: {
          add: "/system/mbpOrder/add",
          edit: "/system/mbpOrder/edit",
          queryById: "/system/mbpOrder/queryById",
          mbpOrderlist: {
            list: '/system/mbpOrder/queryMbpOrderlistByMainId'
          },
        }
      }
    },
    methods: {
      /** 当点击新增按钮时调用此方法 ， 重写混入中的add方法 */
      add() {
        if (typeof this.addBefore === 'function') this.addBefore()
        // 默认新增空数据
        let rowNum = this.addDefaultRowNum
        if (typeof rowNum !== 'number') {
          rowNum = 1
          console.warn('由于你没有在 data 中定义 addDefaultRowNum 或 addDefaultRowNum 不是数字，所以默认添加一条空数据，如果不想默认添加空数据，请将定义 addDefaultRowNum 为 0')
        }
        this.eachAllTable((item) => {
          item.add(rowNum)
        })
        if (typeof this.addAfter === 'function') this.addAfter(this.model)
        this.edit({})
        this.genOrderId()
      },
      /** 发起请求，自动判断是执行新增还是修改操作 */
      request(formData) {
        let url = this.url.add, method = 'post'
        if (this.model.id) {
          url = this.url.edit
          method = 'put'
        }
        this.confirmLoading = true
        httpAction(url, formData, method).then((res) => {
          if (res.success) {
            //this.$message.success(res.message)
            //console.log("id : " + res.result.id)
            if (!this.model.id) {
              this.model.id = res.result.id
              //console.log("id : " + that.model.id)
              //that.form.setFieldsValue({'id': res.result.id})
            }
            this.nextStep()
            //this.$emit('ok')
            //this.close()
          } else {
            this.$message.warning(res.message)
          }
        }).finally(() => {
          this.confirmLoading = false
        })
      },
      /** 接收客户页面传来的客户id,名称 **/
      getCustomerId(id,name) {
        // console.log("id : " + id + "   name : " + name)
        let fieldval = {
          'customerName': name
        }
        this.$nextTick(() => {
          this.model.customerId = id
          this.form.setFieldsValue(fieldval)
        })
      },
      /** 生成一个订单编号 */
      genOrderId() {
        var date = new Date();
        var year = date.getFullYear() // 年份
        var month = date.getMonth() + 1 // 月份
        var day = date.getDate() // 日
        var hour = date.getHours() // 时
        var minute = date.getMinutes() // 分
        var second = date.getSeconds() // 秒
        var millisecond = date.getMilliseconds() // 毫秒
        //console.log(time)
        var orderId = 'CN' + year + month + day + hour + minute + second + millisecond
        var val = {
          'orderId': orderId
        }
        this.$nextTick(() => {
          this.form.setFieldsValue(val)
        })
      },
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        let fieldval = pick(this.model,'orderId','orderTitle','customerId','customerName','salesman','deptId','paymentId','paymentDate','deliveryMethod','deliveryFee','auditPerson','auditTime','currency','orderType','orderState','accessory','contract','disp','createBy','createTime','updateBy','updateTime','delFlag')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.mbpOrderlist.list, params, this.mbpOrderlistTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          mbpOrderlistList: allValues.tablesValue[0].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
     popupCallback(row){
       this.form.setFieldsValue(pick(row,'orderId','orderTitle','customerId','salesman','deptId','paymentId','paymentDate','deliveryMethod','deliveryFee','auditPerson','auditTime','currency','orderType','orderState','accessory','contract','disp','createBy','createTime','updateBy','updateTime','delFlag'))
     },
     handleCategoryChange(value,backObj){
       this.form.setFieldsValue(backObj)
      },
      nextStep () {
        this.$emit('nextStep',this.model)
      },
      prevStep () {
        this.$emit('prevStep',this.model)
      },
      /*当从下一步返回到这一步时，需传入id，加载此条记录*/
      loadForm(id) {
        let params = {id:id};
        getAction(this.url.queryById,params).then((res)=>{
          if(res.success){
            this.model = res.result
            console.log("result : " + JSON.stringify(this.model))
            this.edit (res.result);
          }
        });
      },

    },
    mounted() {
      this.genOrderId()
    }
  }
</script>

<style scoped>
</style>