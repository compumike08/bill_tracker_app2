'use strict';

angular.module('billTrackerApp2App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('payerTransactionLedger', {
                parent: 'entity',
                url: '/payerTransactionLedgers',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'PayerTransactionLedgers'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/payerTransactionLedger/payerTransactionLedgers.html',
                        controller: 'PayerTransactionLedgerController'
                    }
                },
                resolve: {
                }
            })
            .state('payerTransactionLedger.detail', {
                parent: 'entity',
                url: '/payerTransactionLedger/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'PayerTransactionLedger'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/payerTransactionLedger/payerTransactionLedger-detail.html',
                        controller: 'PayerTransactionLedgerDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'PayerTransactionLedger', function($stateParams, PayerTransactionLedger) {
                        return PayerTransactionLedger.get({id : $stateParams.id});
                    }]
                }
            })
            .state('payerTransactionLedger.new', {
                parent: 'payerTransactionLedger',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/payerTransactionLedger/payerTransactionLedger-dialog.html',
                        controller: 'PayerTransactionLedgerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {payer_trsctn_datetime: null, payer_trsctn_amount: null, payer_trsctn_type: null, trsctn_pair_id: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('payerTransactionLedger', null, { reload: true });
                    }, function() {
                        $state.go('payerTransactionLedger');
                    })
                }]
            })
            .state('payerTransactionLedger.edit', {
                parent: 'payerTransactionLedger',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/payerTransactionLedger/payerTransactionLedger-dialog.html',
                        controller: 'PayerTransactionLedgerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PayerTransactionLedger', function(PayerTransactionLedger) {
                                return PayerTransactionLedger.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('payerTransactionLedger', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
