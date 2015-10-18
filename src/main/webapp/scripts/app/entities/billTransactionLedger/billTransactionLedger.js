'use strict';

angular.module('billTrackerApp2App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('billTransactionLedger', {
                parent: 'entity',
                url: '/billTransactionLedgers',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'BillTransactionLedgers'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/billTransactionLedger/billTransactionLedgers.html',
                        controller: 'BillTransactionLedgerController'
                    }
                },
                resolve: {
                }
            })
            .state('billTransactionLedger.detail', {
                parent: 'entity',
                url: '/billTransactionLedger/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'BillTransactionLedger'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/billTransactionLedger/billTransactionLedger-detail.html',
                        controller: 'BillTransactionLedgerDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'BillTransactionLedger', function($stateParams, BillTransactionLedger) {
                        return BillTransactionLedger.get({id : $stateParams.id});
                    }]
                }
            })
            .state('billTransactionLedger.new', {
                parent: 'billTransactionLedger',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/billTransactionLedger/billTransactionLedger-dialog.html',
                        controller: 'BillTransactionLedgerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {bill_trsctn_datetime: null, bill_trsctn_amount: null, bill_trsctn_type: null, trsctn_pair_id: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('billTransactionLedger', null, { reload: true });
                    }, function() {
                        $state.go('billTransactionLedger');
                    })
                }]
            })
            .state('billTransactionLedger.edit', {
                parent: 'billTransactionLedger',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/billTransactionLedger/billTransactionLedger-dialog.html',
                        controller: 'BillTransactionLedgerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['BillTransactionLedger', function(BillTransactionLedger) {
                                return BillTransactionLedger.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('billTransactionLedger', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
