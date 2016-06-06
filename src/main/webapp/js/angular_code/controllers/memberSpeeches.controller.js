module.exports = function (app) {

    app.controller("MemberSpeechesCtrl", MemberSpeechesCtrl);

    MemberSpeechesCtrl.$inject = ['Speeches', 'localStorageService', 'dialogs', 'MemberSpeeches'];
    function MemberSpeechesCtrl(Speeches, localStorageService, dialogs, MemberSpeeches) {

        var ctrl = this;

        ctrl.list = list;
        ctrl.remove = remove;
        ctrl.changePage = changePage;
        ctrl.search = search;
        ctrl.updateSpeech = updateSpeech;
        ctrl.createSpeech = createSpeech;
        ctrl.showText = showText;

        ctrl.speeches = [];
        ctrl.currentPage = null;
        ctrl.searchTerm = null;
        ctrl.member = null;
        // CHECK PAGE MODE - EDIT || CREATE

        //GET ALL
        function list() {
            if (!ctrl.member) {
                if (localStorageService.get("member") != null) {
                    ctrl.member = localStorageService.get("member");
                }
            }
            MemberSpeeches.forMember(ctrl.member.id).getList({
                page: ctrl.currentPage,
                query: ctrl.searchTerm
            }).then(
                function (speeches) {
                    ctrl.speeches = speeches;
                },
                function (response) {
                    dialogs.notify("Error!", response.data.error, {size: "md"});
                });
        }

        //SEARCH
        function search() {
            ctrl.currentPage = 1;
            list();
        }

        // DELETE
        function remove(speechId) {
            var dlg = dialogs.confirm("Are you sure?", "Do you want to delete selected speech?", {size: "md"});
            dlg.result.then(function () {
                Speeches.one(speechId).remove().then(
                    function () {
                        list();
                    }, function (response) {
                        dialogs.notify("Error!", response.data.error, {size: "md"});
                    });
            });
        }

        //CHANGE PAGE
        function changePage(page) {
            ctrl.currentPage = page;
            list();
        }

        function createSpeech() {
            localStorageService.remove("editedSpeech");
            window.location = '#/create-speech';
        }

        function updateSpeech(speech) {
            localStorageService.set("editedSpeech", speech);
            window.location = "#/create-speech";
        }

        function showText(s) {
            dialogs.notify(s.member.name + ' ' + s.member.lastName + ' speech on ' + s.sessionDate, s.text, null);
        }

    }
};
