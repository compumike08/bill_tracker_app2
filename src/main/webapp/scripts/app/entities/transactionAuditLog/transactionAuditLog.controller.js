'use strict';

angular.module('billTrackerApp2App')
    .controller('TransactionAuditLogController', function ($scope, TransactionAuditLog) {
        $scope.transactionAuditLogs = [];
        $scope.loadAll = function() {
            TransactionAuditLog.query(function(result) {
               $scope.transactionAuditLogs = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            TransactionAuditLog.get({id: id}, function(result) {
                $scope.transactionAuditLog = result;
                $('#deleteTransactionAuditLogConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            TransactionAuditLog.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTransactionAuditLogConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.transactionAuditLog = {debit_amount: null, credit_amount: null, trsctn_datetime: null, id: null};
        };
    });
