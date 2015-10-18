'use strict';

angular.module('billTrackerApp2App').controller('BillAmountsOwedDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'BillAmountsOwed', 'PayerAccount', 'Bill',
        function($scope, $stateParams, $modalInstance, entity, BillAmountsOwed, PayerAccount, Bill) {

        $scope.billAmountsOwed = entity;
        $scope.payeraccounts = PayerAccount.query();
        $scope.bills = Bill.query();
        $scope.load = function(id) {
            BillAmountsOwed.get({id : id}, function(result) {
                $scope.billAmountsOwed = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('billTrackerApp2App:billAmountsOwedUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.billAmountsOwed.id != null) {
                BillAmountsOwed.update($scope.billAmountsOwed, onSaveFinished);
            } else {
                BillAmountsOwed.save($scope.billAmountsOwed, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
