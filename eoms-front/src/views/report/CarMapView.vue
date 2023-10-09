<template>
  <div class="carMap">
    <mapCanvas :position="map" :floor="floor + 1"></mapCanvas>
  </div>
</template>

<script>
import map from '@/map/map.json'
import mapCanvas from "@/components/floorMap/map.vue";
import mapCanvas1 from "@/components/floorMap/map1.vue";

export default {
  name: "carMap",
  data() {
    return {
      map: [],
      floor: 0
    }
  },
  components: {
    mapCanvas,
    mapCanvas1
  },
  methods: {
    changeLayer() {

      if(this.floor === 9){
        this.floor = 0;
      }else this.floor += 1;
      this.map = map[this.floor];
    }
  },
  mounted(){
    this.map = map[this.floor];
    this.start = setInterval(() => {
      this.changeLayer()
    }, 30000)
  },
  destroyed(){
    clearInterval(this.start)
  }
}
</script>

<style scoped lang="scss">
.carMap {
  //height: 100vh;
  //overflow: hidden;
  p {
    text-align: center;
    font-size: 30px;
  }
}
</style>