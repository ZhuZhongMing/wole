<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="8">
            <a-form-item label="物料编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['materialId', validatorRules.materialId]" placeholder="请输入物料编码"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="物料名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['materialName', validatorRules.materialName]" placeholder="请输入物料名称"></a-input>
            </a-form-item>
          </a-col>
          <!--<a-col :span="8">
            <a-form-item label="别名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['alias']" placeholder="请输入别名"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="英文名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['englishName']" placeholder="请输入英文名"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="检索码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['indexCode']" placeholder="请输入检索码"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['level']" placeholder="请输入等级"></a-input>
            </a-form-item>
          </a-col>-->
          <a-col :span="8">
            <a-form-item label="型号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['model']" placeholder="请输入型号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="规格" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['special']" placeholder="请输入规格"></a-input>
            </a-form-item>
          </a-col>
          <!--<a-col :span="8">
            <a-form-item label="通用规格" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['generalSpecial']" placeholder="请输入通用规格"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="商标" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['brand']" placeholder="请输入商标"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="ABC类" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['abcCategory']" placeholder="请输入ABC类"></a-input>
            </a-form-item>
          </a-col>-->
          <a-col :span="8">
            <a-form-item label="颜色" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['color']" placeholder="请输入颜色"></a-input>
            </a-form-item>
          </a-col>
          <!--<a-col :span="8">
            <a-form-item label="计算特性1" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['spe1']" placeholder="请输入计算特性1" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="计算特性2" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['spe2']" placeholder="请输入计算特性2" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="条形码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['barcode']" placeholder="请输入条形码"></a-input>
            </a-form-item>
          </a-col>-->
          <a-col :span="8">
            <a-form-item label="物料分类" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-category-select v-decorator="['materialTypeEncode']" pcode="B06" placeholder="请选择物料分类" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['unitid']" placeholder="请输入单位"></a-input>
            </a-form-item>
          </a-col>
          <!--<a-col :span="8">
            <a-form-item label="是否虚拟件0-否,1-是" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['isVirtalPart']" :trigger-change="true" dictCode="" placeholder="请选择是否虚拟件0-否,1-是"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="是否外购件0-否,1-是" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['isPurchasePart']" :trigger-change="true" dictCode="" placeholder="请选择是否外购件0-否,1-是"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="是否通用件0-否,1-是" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['isGeneralPart']" :trigger-change="true" dictCode="" placeholder="请选择是否通用件0-否,1-是"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="安全库存" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['minWhStorge', validatorRules.minWhStorge]" placeholder="请输入安全库存" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="最高库存" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['maxWhStorge', validatorRules.maxWhStorge]" placeholder="请输入最高库存" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="当前库存" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['currentWhStogre', validatorRules.currentWhStogre]" placeholder="请输入当前库存" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="允许销售0-禁止,1-允许" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['isSell']" placeholder="请输入允许销售0-禁止,1-允许" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="序列号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['serialNumber']" placeholder="请输入序列号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="采购单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['purchaseOrganize']" placeholder="请输入采购单位"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="产地" :labelCol="labelCol" :wrapperCol="wrapperCol">
             <j-area-linkage type="cascader" v-decorator="['productAddress']" placeholder="请输入省市区"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="是否入库检验" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['isWhInSpect']" :trigger-change="true" dictCode="" placeholder="请选择是否入库检验"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="成本日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择成本日期" v-decorator="['costDate']" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="标准成本价" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['standardCost']" placeholder="请输入标准成本价" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="销售价" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['salesCost', validatorRules.salesCost]" placeholder="请输入销售价" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="采购价" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['purchaseCost', validatorRules.purchaseCost]" placeholder="请输入采购价" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="采购提前天数" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['prepareDays']" placeholder="请输入采购提前天数" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="工序编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['processId']" placeholder="请输入工序编号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="是否停用0-否,1-是" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['isStop']" :trigger-change="true" dictCode="" placeholder="请选择是否停用0-否,1-是"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="起初数值" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['startNumber']" placeholder="请输入起初数值" style="width: 100%"/>
            </a-form-item>
          </a-col>-->
          <a-col :span="8">
            <a-form-item label="图片" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-image-upload isMultiple v-decorator="['figurePath']"></j-image-upload>
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
  import JImageUpload from '@/components/jeecg/JImageUpload'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JCategorySelect from '@/components/jeecg/JCategorySelect'
  import JAreaLinkage from '@comp/jeecg/JAreaLinkage'

  export default {
    name: 'MbpMaterialForm',
    components: {
      JFormContainer,
      JDate,
      JImageUpload,
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
          materialId: {
            rules: [
              { required: true, message: '请输入物料编码!'},
              { validator: (rule, value, callback) => validateDuplicateValue('mbp_material', 'material_id', value, this.model.id, callback)},
            ]
          },
          materialName: {
            rules: [
              { required: true, message: '请输入物料名称!'},
            ]
          },
          minWhStorge: {
            rules: [
              { required: false},
              { pattern: /^-?\d+$/, message: '请输入整数!'},
            ]
          },
          maxWhStorge: {
            rules: [
              { required: false},
              { pattern: /^-?\d+$/, message: '请输入整数!'},
            ]
          },
          currentWhStogre: {
            rules: [
              { required: false},
              { pattern: /^-?\d+$/, message: '请输入整数!'},
            ]
          },
          salesCost: {
            rules: [
              { required: false},
              { pattern: /^(([1-9][0-9]*)|([0]\.\d{0,2}|[1-9][0-9]*\.\d{0,2}))$/, message: '请输入正确的金额!'},
            ]
          },
          purchaseCost: {
            rules: [
              { required: false},
              { pattern: /^(([1-9][0-9]*)|([0]\.\d{0,2}|[1-9][0-9]*\.\d{0,2}))$/, message: '请输入正确的金额!'},
            ]
          },
        },
        url: {
          add: "/system/mbpMaterial/add",
          edit: "/system/mbpMaterial/edit",
          queryById: "/system/mbpMaterial/queryById"
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
          this.form.setFieldsValue(pick(this.model,'materialId','materialName','alias','englishName','indexCode','level','model','special','generalSpecial','brand','abcCategory','color','spe1','spe2','barcode','materialTypeEncode','unitid','isVirtalPart','isPurchasePart','isGeneralPart','minWhStorge','maxWhStorge','currentWhStogre','isSell','serialNumber','purchaseOrganize','productAddress','isWhInSpect','costDate','standardCost','salesCost','purchaseCost','prepareDays','processId','isStop','startNumber','figurePath','disp','createBy','createTime','updateBy','updateTime','delFlag'))
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
        this.form.setFieldsValue(pick(row,'materialId','materialName','alias','englishName','indexCode','level','model','special','generalSpecial','brand','abcCategory','color','spe1','spe2','barcode','materialTypeEncode','unitid','isVirtalPart','isPurchasePart','isGeneralPart','minWhStorge','maxWhStorge','currentWhStogre','isSell','serialNumber','purchaseOrganize','productAddress','isWhInSpect','costDate','standardCost','salesCost','purchaseCost','prepareDays','processId','isStop','startNumber','figurePath','disp','createBy','createTime','updateBy','updateTime','delFlag'))
      },
      handleCategoryChange(value,backObj){
        this.form.setFieldsValue(backObj)
      }
    }
  }
</script>