'use strict';

angular.module('billTrackerApp2App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('payerAccount', {
                parent: 'entity',
                url: '/payerAccounts',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'PayerAccounts'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/payerAccount/payerAccounts.html',
                        controller: 'PayerAccountController'
                    }
                },
                resolve: {
                }
            })
            .state('payerAccount.detail', {
                parent: 'entity',
                url: '/payerAccount/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'PayerAccount'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/payerAccount/payerAccount-detail.html',
                        controller: 'PayerAccountDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'PayerAccount', function($stateParams, PayerAccount) {
                        return PayerAccount.get({id : $stateParams.id});
                    }]
                }
            })
            .state('payerAccount.new', {
                parent: 'payerAccount',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/payerAccount/payerAccount-dialog.html',
                        controller: 'PayerAccountDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {payer_acct_name: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('payerAccount', null, { reload: true });
                    }, function() {
                        $state.go('payerAccount');
                    })
                }]
            })
            .state('payerAccount.edit', {
                parent: 'payerAccount',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/payerAccount/payerAccount-dialog.html',
                        controller: 'PayerAccountDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PayerAccount', function(PayerAccount) {
                                return PayerAccount.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('payerAccount', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
