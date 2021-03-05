<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="8">
            <a-form-item label="客户名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['customerName', validatorRules.customerName]" placeholder="请输入客户名称"></a-input>
            </a-form-item>
          </a-col>
          <!--<a-col :span="8">
            <a-form-item label="简称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['abcName']" placeholder="请输入简称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="检索码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['indexCode']" placeholder="请输入检索码"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="是否供应商0-否,1-是" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['isSupplier']" :trigger-change="true" dictCode="is_supplier" placeholder="请选择是否供应商0-否,1-是"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="是否客户0-否,1-是" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['isClient']" :trigger-change="true" dictCode="is_client" placeholder="请选择是否客户0-否,1-是"/>
            </a-form-item>
          </a-col>-->
          <a-col :span="8">
            <a-form-item label="客户类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-category-select v-decorator="['customerType']" pcode="B03" placeholder="请选择客户类型" />
            </a-form-item>
          </a-col>
          <!--<a-col :span="8">
            <a-form-item label="客户分类1" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-category-select v-decorator="['categoryType']" pcode="B04" placeholder="请选择客户分类1" />
            </a-form-item>
          </a-col>-->
          <a-col :span="8">
            <a-form-item label="所属区域" :labelCol="labelCol" :wrapperCol="wrapperCol">
             <j-area-linkage type="cascader" v-decorator="['customerArea']" placeholder="请输入省市区"/>
            </a-form-item>
          </a-col>
          <!--<a-col :span="8">
            <a-form-item label="首次合作时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择首次合作时间" v-decorator="['firstCoDate']" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="客户分类2" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-category-select v-decorator="['categoryType2']" pcode="B04" placeholder="请选择客户分类2" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="是否外协0-否,1-是" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['isAssist']" :trigger-change="true" dictCode="is_assist" placeholder="请选择是否外协0-否,1-是"/>
            </a-form-item>
          </a-col>-->
          <a-col :span="8">
            <a-form-item label="客户地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['customerAddress']" placeholder="请输入客户地址"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="邮政编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['postCode', validatorRules.postCode]" placeholder="请输入邮政编码"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="邮箱" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['email', validatorRules.email]" placeholder="请输入邮箱"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="法人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['customerManager']" placeholder="请输入法人"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="联系人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['contactPerson']" placeholder="请输入联系人"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="联系电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['telephone', validatorRules.telephone]" placeholder="请输入联系电话"></a-input>
            </a-form-item>
          </a-col>
          <!--<a-col :span="8">
            <a-form-item label="传真" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['fax', validatorRules.fax]" placeholder="请输入传真"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="开户银行" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['bank']" placeholder="请输入开户银行"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="开户账号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['account', validatorRules.account]" placeholder="请输入开户账号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="付款方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-category-select v-decorator="['paymentKind']" pcode="B05" placeholder="请选择付款方式" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="付款周期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['paymentCircle']" placeholder="请输入付款周期" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="结算货币" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['currency']" placeholder="请输入结算货币"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="税号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['taxNo']" placeholder="请输入税号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="营业执照" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['license']" placeholder="请输入营业执照"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="信用等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['creditGrade']" placeholder="请输入信用等级"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="信用额度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['creditLimit', validatorRules.creditLimit]" placeholder="请输入信用额度" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="业务员" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-select-user-by-dep v-decorator="['salesman']"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="客户启用状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['customerStatus']" :trigger-change="true" dictCode="customer_status" placeholder="请选择客户启用状态"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['description']" rows="4" placeholder="请输入描述"/>
            </a-form-item>
          </a-col>-->
          <a-col :span="8">
          <a-form-item label="企业网址" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['webSite', validatorRules.webSite]" placeholder="请输入企业网址"></a-input>
          </a-form-item>
        </a-col>
          <a-col :span="8">
            <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['disp']" rows="4" placeholder="请输入备注"/>
            </a-form-item>
          </a-col>

          <!--<a-col :span="8">
            <a-form-item label="创建人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['createBy']" placeholder="请输入创建人"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="创建时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择创建时间" v-decorator="['createTime']" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="修改人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['updateBy']" placeholder="请输入修改人"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="修改时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择修改时间" v-decorator="['updateTime']" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="删除标识0-正常,1-已删除" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['delFlag']" :trigger-change="true" dictCode="del_flag" placeholder="请选择删除标识0-正常,1-已删除"/>
            </a-form-item>
          </a-col>-->
          <a-col v-if="showFlowSubmitButton" :span="24" style="text-align: center">
            <a-button @click="submitForm">提 交</a-button>
          </a-col>
        </a-row>
      </a-form>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JFormContainer from '@/components/jeecg/JFormContainer'
  import JDate from '@/components/jeecg/JDate'
  import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JCategorySelect from '@/components/jeecg/JCategorySelect'
  import JAreaLinkage from '@comp/jeecg/JAreaLinkage'

  export default {
    name: 'MbpCustomerForm',
    components: {
      JFormContainer,
      JDate,
      JSelectUserByDep,
      JDictSelectTag,
      JAreaLinkage,
      JCategorySelect,
    },
    props: {
      //流程表单data
      formData: {
        type: Object,
        default: ()=>{},
        required: false
      },
      //表单模式：true流程表单 false普通表单
      formBpm: {
        type: Boolean,
        default: false,
        required: false
      },
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        form: this.$form.createForm(this),
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
          customerName: {
            rules: [
              { required: true, message: '请输入客户名称!'},
              /*{ validator: (rule, value, callback) => validateDuplicateValue('mbp_customer', 'customer_name', value, this.model.id, callback)},*/
            ]
          },
          postCode: {
            rules: [
              { required: false},
              { pattern: /^[1-9]\d{5}$/, message: '请输入正确的邮政编码!'},
            ]
          },
          telephone: {
            rules: [
              { required: false},
              { pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号码!'},
            ]
          },
          email: {
            rules: [
              { required: false},
              { pattern: /^([\w]+\.*)([\w]+)@[\w]+\.\w{3}(\.\w{2}|)$/, message: '请输入正确的电子邮件!'},
            ]
          },
          fax: {
            rules: [
              { required: false},
              { pattern: /^-?\d+\.?\d*$/, message: '请输入数字!'},
            ]
          },
          account: {
            rules: [
              { required: false},
              { pattern: /^-?\d+$/, message: '请输入整数!'},
            ]
          },
          creditLimit: {
            rules: [
              { required: false},
              { pattern: /^(([1-9][0-9]*)|([0]\.\d{0,2}|[1-9][0-9]*\.\d{0,2}))$/, message: '请输入正确的金额!'},
            ]
          },
          webSite: {
            rules: [
              { required: false},
              { pattern: /^(?:([A-Za-z]+):)?(\/{0,3})([0-9.\-A-Za-z]+)(?::(\d+))?(?:\/([^?#]*))?(?:\?([^#]*))?(?:#(.*))?$/, message: '请输入正确的网址!'},
            ]
          },
        },
        url: {
          add: "/system/mbpCustomer/add",
          edit: "/system/mbpCustomer/edit",
          queryById: "/system/mbpCustomer/queryById"
        }
      }
    },
    computed: {
      formDisabled(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return false
          }
          return true
        }
        return this.disabled
      },
      showFlowSubmitButton(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return true
          }
        }
        return false
      }
    },
    created () {
      //如果是流程中表单，则需要加载流程表单data
      this.showFlowData();
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'customerName','abcName','indexCode','isSupplier','isClient','customerType','categoryType','customerArea','firstCoDate','categoryType2','isAssist','customerAddress','postCode','customerManager','contactPerson','telephone','email','fax','bank','account','paymentKind','paymentCircle','currency','taxNo','license','creditGrade','creditLimit','salesman','customerStatus','description','disp','webSite','createBy','createTime','updateBy','updateTime','delFlag'))
        })
      },
      //渲染流程表单数据
      showFlowData(){
        if(this.formBpm === true){
          let params = {id:this.formData.dataId};
          getAction(this.url.queryById,params).then((res)=>{
            if(res.success){
              this.edit (res.result);
            }
          });
        }
      },
      submitForm () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }

        })
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'customerName','abcName','indexCode','isSupplier','isClient','customerType','categoryType','customerArea','firstCoDate','categoryType2','isAssist','customerAddress','postCode','customerManager','contactPerson','telephone','email','fax','bank','account','paymentKind','paymentCircle','currency','taxNo','license','creditGrade','creditLimit','salesman','customerStatus','description','disp','webSite','createBy','createTime','updateBy','updateTime','delFlag'))
      },
      handleCategoryChange(value,backObj){
        this.form.setFieldsValue(backObj)
      }
    }
  }
</script>