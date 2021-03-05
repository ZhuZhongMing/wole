<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="8">
            <a-form-item label="客户名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['customername']" placeholder="请输入客户名称"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="客户编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['customerid']" placeholder="请输入客户编号"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="机床名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['cncname']" placeholder="请输入机床名称"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="机床编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['cncsn']" placeholder="请输入机床编号"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="机床类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['cnctype']" placeholder="请输入机床类型"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="机床ip" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['cncip']" placeholder="请输入机床ip"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="机床模式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['cncmodel']" placeholder="请输入机床模式"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="机床状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['cncstate']" placeholder="请输入机床状态"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="通电时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['powerntime']" placeholder="请输入通电时间"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="运行时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['runningtime']" placeholder="请输入运行时间"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="加工时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['cuttingtime']" placeholder="请输入加工时间"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="当前加工零件名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['partname']" placeholder="请输入当前加工零件名"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="当前加工程序名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['programname']" placeholder="请输入当前加工程序名"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="已加工数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['count']" placeholder="请输入已加工数量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="目标加工数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['targetcount']" placeholder="请输入目标加工数量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="主轴负载" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['spindleload']" placeholder="请输入主轴负载" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="主轴转速" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['spindlespeed']" placeholder="请输入主轴转速" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="主轴倍率" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['spindlerate']" placeholder="请输入主轴倍率" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="主轴设定转速" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['spindlespeedset']" placeholder="请输入主轴设定转速" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="进给轴转速" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['feedspeed']" placeholder="请输入进给轴转速" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="进给轴倍率" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['feedrate']" placeholder="请输入进给轴倍率" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="进给轴设定转速" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['feedspeedset']" placeholder="请输入进给轴设定转速" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="报警类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['alarmtype']" placeholder="请输入报警类型"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="报警号码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['alarmnum']" placeholder="请输入报警号码"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="报警描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['alarminfo']" placeholder="请输入报警描述"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="刀具号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['toolnum']" placeholder="请输入刀具号" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="X轴负载" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['xload']" placeholder="请输入X轴负载" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="Y轴负载" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['yload']" placeholder="请输入Y轴负载" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="Z轴负载" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['zload']" placeholder="请输入Z轴负载" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="A轴负载" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['aload']" placeholder="请输入A轴负载" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="B轴负载" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['bload']" placeholder="请输入B轴负载" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="C轴负载" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['cload']" placeholder="请输入C轴负载" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="IO采集功率" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['iopower']" placeholder="请输入IO采集功率" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="采集时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择采集时间" v-decorator="['datatime']" :trigger-change="true" style="width: 100%" />
            </a-form-item>
          </a-col>
          <!--<a-col :span="8">
            <a-form-item label="创建人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['createBy']" placeholder="请输入创建人"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="创建时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择创建时间" v-decorator="['createTime']" :trigger-change="true" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="修改人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['updateBy']" placeholder="请输入修改人"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="修改时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择修改时间" v-decorator="['updateTime']" :trigger-change="true" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="是否删除" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['delFlag']" placeholder="请输入是否删除" style="width: 100%" />
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

  export default {
    name: 'CncModelForm',
    components: {
      JFormContainer,
      JDate,
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
        },
        url: {
          add: "/mqtt/cncModel/add",
          edit: "/mqtt/cncModel/edit",
          queryById: "/mqtt/cncModel/queryById"
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
          this.form.setFieldsValue(pick(this.model,'customername','customerid','cncname','cncsn','cnctype','cncip','cncmodel','cncstate','powerntime','runningtime','cuttingtime','partname','programname','count','targetcount','spindleload','spindlespeed','spindlerate','spindlespeedset','feedspeed','feedrate','feedspeedset','alarmtype','alarmnum','alarminfo','toolnum','xload','yload','zload','aload','bload','cload','iopower','datatime','createBy','createTime','updateBy','updateTime','delFlag'))
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
        this.form.setFieldsValue(pick(row,'customername','customerid','cncname','cncsn','cnctype','cncip','cncmodel','cncstate','powerntime','runningtime','cuttingtime','partname','programname','count','targetcount','spindleload','spindlespeed','spindlerate','spindlespeedset','feedspeed','feedrate','feedspeedset','alarmtype','alarmnum','alarminfo','toolnum','xload','yload','zload','aload','bload','cload','iopower','datatime','createBy','createTime','updateBy','updateTime','delFlag'))
      },
    }
  }
</script>