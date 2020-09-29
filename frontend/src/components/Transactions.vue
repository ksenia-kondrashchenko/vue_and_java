<template>
    <div id="root">
        <div id="balance">
            <h3>Balance</h3>
            <button v-on:click="loadCurrentBalance">View balance</button>
            <p> {{ balance }}</p>
        </div>

        <div id="transactions">
            <h3>Transactions</h3>
            <button v-on:click="loadAllTransactions">View transactions</button>
            <ul>
                <li v-for="transaction in allTransactions" :key="transaction.id">{{ transaction.id }} - {{
                    transaction.type }} - {{ transaction.amount }}
                </li>
            </ul>

        </div>

        <div id="transactionById">
            <h3>Get Transaction by ID</h3>
            <input type="text" id="transactionIdInput" value="Transaction id...">
            <button v-on:click="loadTransactionById">View transaction</button>
            <div>
                <p>{{ transactionById.id }}</p>
                <p>{{ transactionById.type }}</p>
                <p>{{ transactionById.amount }}</p>
                <p>{{ transactionById.effectiveDate }}</p>
            </div>
        </div>

        <div id="createTransaction">
            <h3>Add New Transaction</h3>
            <input type="text" id="transactionTypeInput" value="Transaction type...">
            <input type="text" id="transactionAmountInput" value="Transaction amount...">
            <button v-on:click="postTransaction">Add transaction</button>
            <div>
                <p>{{ postTransactionResponse.id }}</p>
                <p>{{ postTransactionResponse.type }}</p>
                <p>{{ postTransactionResponse.amount }}</p>
                <p>{{ postTransactionResponse.effectiveDate }}</p>
            </div>
        </div>
    </div>
</template>

<script>
import {AXIOS} from './http-common'

export default {
   name: 'Transactions',
   data() {
       return {
           balance: 0,
           allTransactions: [],
           transactionById: {
                id: '',
                type: '',
                amount: 0,
                effectiveDate: ''
           },
           postTransactionData: {
                type: '',
                amount: 0
           },
           postTransactionResponse: {
                id: '',
                type: '',
                amount: 0,
                effectiveDate: ''
           }
       }
   },
   methods: {
       loadCurrentBalance() {
            AXIOS.get('/account')
           .then(response => {
               this.$data.balance = response.data;
           })
           .catch(error => {
               console.log('ERROR: ' + error.response.data);
           })
       },

       loadAllTransactions() {
           AXIOS.get('/account/transactions')
           .then(response => {
               this.$data.allTransactions = response.data;
           })
           .catch(error => {
               console.log('ERROR: ' + error.response.data);
           })
       },
       loadTransactionById() {
           var id = document.getElementById("transactionIdInput").value;
           AXIOS.get('/account/transactions/' + id)
           .then(response => {
               this.$data.transactionById = response.data;
           })
           .catch(error => {
               console.log('ERROR: ' + error.response.data);
           })
       },
       postTransaction() {
           var type = document.getElementById("transactionTypeInput").value;
           var amount = document.getElementById("transactionAmountInput").value;
           AXIOS.post('/account/transactions', { type: type, amount: amount })
           .then(response => {
               this.$data.postTransactionResponse = response.data;
           })
           .catch(error => {
               console.log('ERROR: ' + error.response.data);
           })
       }
   },
   mounted() {
       this.loadCurrentBalance();
   }
}



</script>