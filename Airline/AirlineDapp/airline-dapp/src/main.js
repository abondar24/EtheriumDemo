import {createApp} from 'vue'
import App from './App.vue'
import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import {library} from '@fortawesome/fontawesome-svg-core'
import {faPlane} from '@fortawesome/free-solid-svg-icons'
import {faShare} from '@fortawesome/free-solid-svg-icons'
import {faMoneyBill} from '@fortawesome/free-solid-svg-icons'
import {faHandshake} from '@fortawesome/free-solid-svg-icons'
import {faReply} from "@fortawesome/free-solid-svg-icons";
import {faUser} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome'

library.add(faPlane,faShare,faMoneyBill,faHandshake,faReply,faUser)


createApp(App).component('font-awesome-icon', FontAwesomeIcon).mount('#app')
