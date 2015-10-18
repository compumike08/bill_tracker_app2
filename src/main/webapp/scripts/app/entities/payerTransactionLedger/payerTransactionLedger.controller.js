'use strict';

angular.module('billTrackerApp2App')
    .controller('PayerTransactionLedgerController', function ($scope, PayerTransactionLedger) {
        $scope.payerTransactionLedgers = [];
        $scope.loadAll = function() {
            PayerTransactionLedger.query(function(result) {
               $scope.payerTransactionLedgers = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PayerTransactionLedger.get({id: id}, function(result) {
                $scope.payerTransactionLedger = result;
                $('#deletePayerTransactionLedgerConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PayerTransactionLedger.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePayerTransactionLedgerConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.payerTransactionLedger = {payer_trsctn_datetime: null, payer_trsctn_amount: null, payer_trsctn_type: null, trsctn_pair_id: null, id: null};
        };
    });
