module.exports = function (app) {

    app.controller("SpeechesCtrl", SpeechesCtrl);

    SpeechesCtrl.$inject = ['Speeches'];
    function SpeechesCtrl(Speeches) {

        var ctrl = this;
        ctrl.create = create;
        ctrl.remove = remove;
        //ctrl.update = update;

        ctrl.speeches = [];
        list();
        ctrl.newSpeech = {};

        //GET ALL
        function list() {
            Speeches.getList().then(function (speeches) {
                ctrl.speeches = speeches;
            });
        }

        //CREATE
        function create() {
            Speeches.post(ctrl.newSpeech).then(function (response) {
                ctrl.newSpeech = {};
                console.log(JSON.stringify(response));
            });
        }

        // DELETE
        function remove(speechId) {
            Speeches.one(speechId).remove().then(function () {
                ctrl.list();
            });
        }
    }
};
