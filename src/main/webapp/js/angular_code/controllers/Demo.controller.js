module.exports = function (app) {

    app.controller("DemoCtrl", DemoCtrl);

    DemoCtrl.$inject = ['Members', 'Speeches', 'Sessions', 'Towns', 'Parties'];
    function DemoCtrl(Members, Speeches, Sessions, Towns, Parties) {

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
