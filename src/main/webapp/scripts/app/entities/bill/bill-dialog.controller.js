'use strict';

angular.module('billTrackerApp2App').controller('BillDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Bill', 'BillAmountsOwed', 'BillTransactionLedger',
        function($scope, $stateParams, $modalInstance, entity, Bill, BillAmountsOwed, BillTransactionLedger) {

        $scope.bill = entity;
        $scope.billamountsoweds = BillAmountsOwed.query();
        $scope.billtransactionledgers = BillTransactionLedger.query();
        $scope.load = function(id) {
            Bill.get({id : id}, function(result) {
                $scope.bill = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('billTrackerApp2App:billUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.bill.id != null) {
                Bill.update($scope.bill, onSaveFinished);
            } else {
                Bill.save($scope.bill, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
