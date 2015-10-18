'use strict';

angular.module('billTrackerApp2App').controller('TransactionAuditLogDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'TransactionAuditLog', 'PayerAccount',
        function($scope, $stateParams, $modalInstance, entity, TransactionAuditLog, PayerAccount) {

        $scope.transactionAuditLog = entity;
        $scope.payeraccounts = PayerAccount.query();
        $scope.load = function(id) {
            TransactionAuditLog.get({id : id}, function(result) {
                $scope.transactionAuditLog = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('billTrackerApp2App:transactionAuditLogUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.transactionAuditLog.id != null) {
                TransactionAuditLog.update($scope.transactionAuditLog, onSaveFinished);
            } else {
                TransactionAuditLog.save($scope.transactionAuditLog, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
