<template>
  <transition name="modal-fade">
    <div class="modal-overlay" @click="$emit('close-modal')">
      <div class="modal" :style="{ height: divHeight + 'px' }" @click.stop>
        <slot></slot>
      </div>
      <div class="close" @click="$emit('close-modal')">
        <img class="close-img" src="../assets/close-icon.svg" alt=""/>
      </div>
    </div>
  </transition>
</template>

<script>
export default {
  props: ['divHeight'],
  created() {
    const escapeHandler = (e) => {
      if (e.key === 'Escape' && this.escapableVisible) {
        this.escapeHandler();
      }
    }

    document.addEventListener('keydown', escapeHandler);
  },
  setup() {
    const escapeHandler = (e) => {
      if (e.key === 'Escape' && this.escapableVisible) {
        this.escapeHandler();
      }
    }

    document.removeEventListener('keydown', escapeHandler);
  }
}
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  display: flex;
  justify-content: center;
  background-color: #0000007a;
}

.modal {
  text-align: center;
  background-color: white;
  height: 440px;
  width: 500px;
  margin-top: 10%;
  padding: 60px 0;
  border-radius: 20px;
  display: inline;
  position: relative;
}

.close {
  margin: 10% 0 0 16px;
  cursor: pointer;
}

.close-img {
  width: 25px;
}

.check {
  width: 150px;
}

h6 {
  font-weight: 500;
  font-size: 28px;
  margin: 20px 0;
}

p {
  /* font-weight: 500; */
  font-size: 16px;
  margin: 20px 0;
}

button {
  background-color: #ac003e;
  width: 150px;
  height: 40px;
  color: white;
  font-size: 14px;
  border-radius: 16px;
  margin-top: 50px;
}

.modal-fade-enter,
.modal-fade-leave-to {
  opacity: 0;
}

.modal-fade-enter-active,
.modal-fade-leave-active {
  transition: opacity 0.5s ease;
}
</style>