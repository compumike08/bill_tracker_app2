'use strict';

angular.module('billTrackerApp2App').controller('BillTransactionLedgerDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'BillTransactionLedger', 'Bill',
        function($scope, $stateParams, $modalInstance, entity, BillTransactionLedger, Bill) {

        $scope.billTransactionLedger = entity;
        $scope.bills = Bill.query();
        $scope.load = function(id) {
            BillTransactionLedger.get({id : id}, function(result) {
                $scope.billTransactionLedger = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('billTrackerApp2App:billTransactionLedgerUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.billTransactionLedger.id != null) {
                BillTransactionLedger.update($scope.billTransactionLedger, onSaveFinished);
            } else {
                BillTransactionLedger.save($scope.billTransactionLedger, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
