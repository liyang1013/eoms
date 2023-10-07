<template>
  <div>
    <p style="font-size: 13px; background-color: #032338;color: rgb(30 167 221) ;text-align: center;line-height: 15px">{{floor}}楼小车运行轨迹</p>
    <canvas id="floorMap">
      您的浏览器不支持canvas，请升级或选择其他浏览器
    </canvas>
    <canvas id="car">
      您的浏览器不支持canvas，请升级或选择其他浏览器
    </canvas>
  </div>
</template>

<script>
export default {
  name: "mapCanvas",
  props: {
    position: {
      type: Array
    },
    floor: {
      type: Number
    }
  },
  methods: {
    fullCanvasScreen() {
      const map = document.getElementById("floorMap")
      map.width = window.innerWidth;
      map.height = window.innerHeight;

      const car = document.getElementById("car");
      car.width = window.innerWidth;
      car.height = window.innerHeight;

    },
    reversalCanvas() {
      const map = document.getElementById("floorMap");
      const mapContext = map.getContext('2d');
      mapContext.scale(1, -1);
      mapContext.translate(0, - map.height);

      const car = document.getElementById("car");
      const carContext = car.getContext('2d');
      carContext.scale(1, -1);
      carContext.translate(0, - car.height);
    },
    initMap() {
      const canvas = document.getElementById("floorMap");
      const c = canvas.getContext('2d');
      c.clearRect(0, 0, canvas.width, canvas.height);

      this.position.forEach(e => {
        //画线
        if (e.line && e.line.length) {
          c.lineWidth = 2;
          c.strokeStyle = "#909399";
          c.beginPath();
          e.line.forEach(l => {
            this.position.filter(p => p.id === l).forEach(o => {
              c.moveTo((e.position[0] - 49) * 9 + 2.5, (e.position[1] - 25.5) * 7 + 2.5);
              c.lineTo((o.position[0] - 49) * 9 + 2.5, (o.position[1] - 25.5) * 7 + 2.5);
              // c.moveTo((e.position[0] - 45) * 18 + 7.5, (e.position[1] - 22) * 20 + 7.5);
              // c.lineTo((o.position[0] - 45) * 18 + 7.5, (o.position[1] - 22) * 20 + 7.5);
            })
          })
          c.closePath();
          c.stroke();
        }

        //画库位
        const image = new Image();
        if (e.type === 11) {
          image.src = "/static/location/inout.png";
        } else if (e.type === 6) {
          image.src = "/static/location/parking.png";
        } else if (e.type === 3) {
          image.src = "/static/location/change.png";
        } else if(e.type === 8) image.src = "/static/location/road.png";
        image.onload = function () {
          // c.drawImage(this, (e.position[0] - 45) * 18, (e.position[1] - 22) * 20, 15, 15)
          c.drawImage(this, (e.position[0] - 49) * 9, (e.position[1] - 25.5) * 7, 5, 5)

        }
      })
    },
    initCar(){
      const canvas = document.getElementById("car");
      const c = canvas.getContext('2d');
      c.clearRect(0, 0, canvas.width, canvas.height);
      this.$http.post('http://172.17.200.154:8090/rest/robots/states',{params: {id: []}}).then(res => {
        res.data.data.filter(e => e.position.floorId === this.floor).forEach(e =>{

          // c.font="10px";
          // c.fillText(e.id,(e.position.x - 45) * 18 + 5, (e.position.y - 22) * 20);
          // c.fillText(e.id,(e.position.x - 49) * 9  , (e.position.y - 25) * 7 );

          const image = new Image();
          image.src = "/static/location/car.png";
          image.onload = function () {
            // c.drawImage(this, (e.position.x - 45) * 18 , (e.position.y - 22) * 20 , 15, 15)
            c.drawImage(this, (e.position.x - 49) * 9 - 1.5, (e.position.y - 25.5) * 7 -1.5 , 8, 8)

          }
        })
      });
    }
  },
  watch: {
    position: function () {
      this.initMap();
    }
  },
  mounted() {
    this.fullCanvasScreen();
    // this.reversalCanvas();
    this.initMap();
    this.start = setInterval(() => {
      this.initCar();
    }, 1000)

    // window.addEventListener('resize', () => {
    //   this.fullCanvasScreen();
    //   this.initMap();
    // });
  },
  destroyed() {
    clearInterval(this.start)
  }
}
</script>
<style lang="scss">
#floorMap {
  background-color: #032338;
  position: absolute;
  overflow:auto;
  overflow-y:hidden;
}
#car {
  position: absolute;
  overflow:auto;
  overflow-y:hidden;
}
</style>
