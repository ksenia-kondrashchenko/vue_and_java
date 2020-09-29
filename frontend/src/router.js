import Vue from 'vue'
import Router from 'vue-router'
import Transactions from '@/components/Transactions'

Vue.use(Router)

export default new Router({
   mode: 'history',
   routes: [
     {
       path: '/',
       name: 'Transactions',
       component: Transactions
     }
   ]
})