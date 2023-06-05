import { createApp } from 'vue'
import App from './App.vue'
import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import store from "@/store";

import './assets/main.css'

createApp(App)
    .use(store)
    .mount('#app')
