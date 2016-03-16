module.exports = function (app) {

    app.controller("DemoCtrl", DemoCtrl);

    DemoCtrl.$inject = ['Members'];
    function DemoCtrl (Members) {

        var ctrl = this;
        ctrl.createMember = createMember;

        ctrl.newMember = {};

        function createMember() {
            Members.post(ctrl.newMember).then(function (response) {
                ctrl.newMember = {};
                console.log(JSON.stringify(response));
            });
        }



    }

};
