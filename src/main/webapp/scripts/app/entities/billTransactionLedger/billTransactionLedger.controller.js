'use strict';

angular.module('billTrackerApp2App')
    .controller('BillTransactionLedgerController', function ($scope, BillTransactionLedger) {
        $scope.billTransactionLedgers = [];
        $scope.loadAll = function() {
            BillTransactionLedger.query(function(result) {
               $scope.billTransactionLedgers = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            BillTransactionLedger.get({id: id}, function(result) {
                $scope.billTransactionLedger = result;
                $('#deleteBillTransactionLedgerConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            BillTransactionLedger.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteBillTransactionLedgerConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.billTransactionLedger = {bill_trsctn_datetime: null, bill_trsctn_amount: null, bill_trsctn_type: null, trsctn_pair_id: null, id: null};
        };
    });
