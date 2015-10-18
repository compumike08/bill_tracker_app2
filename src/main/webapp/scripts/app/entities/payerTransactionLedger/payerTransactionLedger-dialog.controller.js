'use strict';

angular.module('billTrackerApp2App').controller('PayerTransactionLedgerDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'PayerTransactionLedger', 'PayerAccount',
        function($scope, $stateParams, $modalInstance, entity, PayerTransactionLedger, PayerAccount) {

        $scope.payerTransactionLedger = entity;
        $scope.payeraccounts = PayerAccount.query();
        $scope.load = function(id) {
            PayerTransactionLedger.get({id : id}, function(result) {
                $scope.payerTransactionLedger = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('billTrackerApp2App:payerTransactionLedgerUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.payerTransactionLedger.id != null) {
                PayerTransactionLedger.update($scope.payerTransactionLedger, onSaveFinished);
            } else {
                PayerTransactionLedger.save($scope.payerTransactionLedger, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
