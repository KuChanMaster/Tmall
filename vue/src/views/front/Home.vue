<template>
  <div class="main-content">
    <div style="height: 55px;background-color: #C566F6FF"> </div>

    <div style="display: flex">
      <div class="left"> </div>
      <div style="width: 66%;height: 1000px ;background-color: white">
            <div style="color:#FE0137FF; margin: 15px 0px 15px 18px; font-weight: bold; font-size: 16px">主题市场</div>
            <div style="display: flex;color: #666666FF; margin:0 25px">
              <!--商品的分类信息-->
              <div style="flex: 2; height: 300px; background-color: white">
                <div style="display: flex; color:#666666; margin: 14px 0" v-for="item in typeData">
                  <img :src="item.img" style="height: 17px; width: 17px;" alt=""></img>
                  <div style="margin-bottom: 5px; margin-left: 10px; font-size: 10px">{{item.name}}</div>
                </div>
              </div>
              <!--轮播图-->
              <div style="flex: 5; height: 300px; background-color: red"></div>

              <div style="flex: 3; height: 300px; background-color: #2a60c9"></div>
            </div>

      </div>
      <div class="right"> </div>
    </div>
  </div>
</template>

<script>

export default {

  data() {
    return {
        typeData:[]
    }
  },
  mounted() {
      this.loadType()
  },
  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
      loadType(){
        this.$request.get('/type/selectAll').then(res=>{
          if(res. code==='200'){
              this.typeData=res.data
          }else {
            this.$message.error(res.msg)
          }

        })
      }
  }
}
</script>
<style scoped>
.main-content {
  min-height: 100vh;
  /*overflow: hidden;*/
  background-size: 100%;
  background-image: url('@/assets/imgs/img.png');
}
.left {
  width: 17%;
  background-repeat: no-repeat;
  background-image: url('@/assets/imgs/left-img.png');
}
.right {
  width: 17%;
  background-repeat: no-repeat;
  background-image: url('@/assets/imgs/right-img.png')
}
.el-col-5{
  width: 20%;
  max-width: 20%;
  padding: 10px 10px;
}
</style>