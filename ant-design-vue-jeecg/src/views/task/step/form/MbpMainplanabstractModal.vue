<template>

    <a-spin :spinning="confirmLoading">
      <!-- 主表单区域 -->
      <a-form :form="form">
        <a-row>
          <a-col :xs="24" :sm="12">
            <a-form-item label="部门编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-select-depart v-decorator="['deptId', validatorRules.deptId]" multi/>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="订单编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <!--<j-dict-select-tag type="list" v-decorator="['orderId', validatorRules.orderId]" :trigger-change="true" dictCode="mbp_order,order_title,id" placeholder="请选择订单编号" disabled/>-->
              <!--<a-select v-decorator="['orderId', validatorRules.orderId]" placeholder="请选择订单编号" disabled></a-select>-->
              <a-input v-decorator="['orderTitle', validatorRules.orderTitle]" placeholder="请选择订单编号" disabled></a-input>
            </a-form-item>
          </a-col>
          <!--<a-col :xs="24" :sm="12">
            <a-form-item label="业务员编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-select-user-by-dep v-decorator="['salesmanId']"/>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="计划种类编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['plantypeId']" placeholder="请输入计划种类编号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="业务日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择业务日期" v-decorator="['saleDate']" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="审核人编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-select-user-by-dep v-decorator="['auditorId']"/>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="审核日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择审核日期" v-decorator="['auditDate']" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="审核状态0-待审,1-打回,2-通过" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['auditStatus']" :trigger-change="true" dictCode="audit_status" placeholder="请选择审核状态0-待审,1-打回,2-通过"/>
            </a-form-item>
          </a-col>-->
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
              <j-date placeholder="请选择制单日期" v-decorator="['createTime']" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
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
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="生产计划明细" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="mbpMainplanTable.loading"
            :columns="mbpMainplanTable.columns"
            :dataSource="mbpMainplanTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="false"/>
        </a-tab-pane>

      </a-tabs>

      <a-row>
        <a-col :span="24" style="text-align: right">
          <a-form-item :wrapperCol="{span: 19, offset: 5}">
            <!--<a-button type="primary" @click="nextStep">提交</a-button>-->
            <a-button @click="prevStep">上一步</a-button>
            <a-button style="margin-left: 8px" type="primary" @click="handleOk">提交</a-button>
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
  import JSelectDepart from '@/components/jeecgbiz/JSelectDepart'
  import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import { getAction, httpAction } from '@/api/manage'

  export default {
    name: 'MbpMainplanabstractModal',
    mixins: [JEditableTableMixin],
    components: {
      JDate,
      JSelectDepart,
      JSelectUserByDep,
      JDictSelectTag,
    },
    data() {
      return {
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
          deptId: {
            rules: [
              { required: true, message: '请选择部门!'},
            ]
          },
          orderId: {
            rules: [
              { required: true, message: '请选择订单!'},
            ]
          },
          orderTitle: {
            rules: [
              { required: true, message: '请选择订单!'},
            ]
          },
        },
        refKeys: ['mbpMainplan', ],
        tableKeys:['mbpMainplan', ],
        activeKey: 'mbpMainplan',
        // 生产计划明细
        mbpMainplanTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '产品名称',
              key: 'materialId',
              type: FormTypes.select,
              dictCode:"mbp_product_map,product_name,id",
              width:"200px",
              placeholder: '请输入${title}',
              disabled:true,
              defaultValue: '',
            },
            {
              title: '数量',
              key: 'number',
              type: FormTypes.inputNumber,
              width:"200px",
              placeholder: '请输入${title}',
              disabled: true,
              defaultValue: '',
              validateRules: [{ pattern: "z", message: "${title}格式不正确" },{ required: true, message: '${title}不能为空' }],
            },
            /*{
              title: '开始时间',
              key: 'startTime',
              type: FormTypes.date,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '完成时间',
              key: 'finishTime',
              type: FormTypes.date,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },*/
            /*{
              title: '计划类型编号',
              key: 'planTypeId',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '客户名称',
              key: 'customenrName',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '加工顺序',
              key: 'processOrder',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '版本号',
              key: 'versionNo',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },*/
            {
              title: '图号',
              key: 'figureNo',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '设备编号',
              key: 'equipmentId',
              type: FormTypes.select,
              dictCode:"mbp_dev_equipment,equipment_name,equipment_id",
              width:"200px",
              placeholder: '请输入${title}',
              validateRules: [{ required: true, message: '${title}不能为空' }],
              defaultValue: '',
            },
            /*{
              title: '优先级编号',
              key: 'priorityId',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },*/
            /*{
              title: '计划状态编号0-等待确认,1-正在进行,2-计划完成',
              key: 'planstatusId',
              type: FormTypes.select,
              dictCode:"planstatus_id",
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '是否关闭',
              key: 'isClosed',
              type: FormTypes.input,
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
              type: FormTypes.select,
              dictCode:"del_flag",
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },*/
          ]
        },
        url: {
          add: "/system/mbpMainplanabstract/add",
          edit: "/system/mbpMainplanabstract/edit",
          mbpMainplan: {
            list: '/system/mbpMainplanabstract/queryMbpMainplanByMainId'
          },
          queryListToPlan: '/system/mbpOrder/queryListToPlan'
        }
      }
    },
    methods: {
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        let fieldval = pick(this.model,'deptId','orderId','salesmanId','plantypeId','saleDate','auditorId','auditDate','auditStatus','disp','createBy','createTime','updateBy','updateTime','delFlag')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.mbpMainplan.list, params, this.mbpMainplanTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          mbpMainplanList: allValues.tablesValue[0].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
      popupCallback(row){
       this.form.setFieldsValue(pick(row,'deptId','orderId','salesmanId','plantypeId','saleDate','auditorId','auditDate','auditStatus','disp','createBy','createTime','updateBy','updateTime','delFlag'))
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
            //this.$emit('ok')
            //this.close()
            this.nextStep ()
          } else {
            this.$message.warning(res.message)
          }
        }).finally(() => {
          this.confirmLoading = false
        })
      },
      nextStep () {
        this.$emit('nextStep')
      },
      prevStep () {
        this.$emit('prevStep',this.model)
      },
      /*接收订单页面传来的orderId*/
      getOrderId(id, title) {
        console.log(id + "  " + title)
        let fieldval = {
          'orderTitle': title
        }
        this.$nextTick(() => {
          this.model.orderId = id
          this.form.setFieldsValue(fieldval)
        })
        /*获取订单明细中数量生成对应生产计划*/
        let params = {id:id};
        getAction(this.url.queryListToPlan,params).then((res)=>{
          if(res.success){
            console.log("result : " + JSON.stringify(res.result))
            this.mbpMainplanTable.dataSource = res.result
            //this.edit (res.result);
          }
        });


      }

    }
  }
</script>

<style scoped>
</style>