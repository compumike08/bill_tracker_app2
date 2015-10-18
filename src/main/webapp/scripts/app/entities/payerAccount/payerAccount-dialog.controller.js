'use strict';

angular.module('billTrackerApp2App').controller('PayerAccountDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'PayerAccount', 'TransactionAuditLog', 'PayerTransactionLedger', 'BillAmountsOwed',
        function($scope, $stateParams, $modalInstance, entity, PayerAccount, TransactionAuditLog, PayerTransactionLedger, BillAmountsOwed) {

        $scope.payerAccount = entity;
        $scope.transactionauditlogs = TransactionAuditLog.query();
        $scope.payertransactionledgers = PayerTransactionLedger.query();
        $scope.billamountsoweds = BillAmountsOwed.query();
        $scope.load = function(id) {
            PayerAccount.get({id : id}, function(result) {
                $scope.payerAccount = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('billTrackerApp2App:payerAccountUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.payerAccount.id != null) {
                PayerAccount.update($scope.payerAccount, onSaveFinished);
            } else {
                PayerAccount.save($scope.payerAccount, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
