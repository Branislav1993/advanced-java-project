module.exports = function (app) {

    app.controller("CreateSpeechCtrl", CreateSpeechCtrl);

    CreateSpeechCtrl.$inject = ['Members', 'Speeches', 'Sessions', 'localStorageService', 'Restangular', 'dialogs'];
    function CreateSpeechCtrl(Members, Speeches, Sessions, localStorageService, Restangular, dialogs) {

        var ctrl = this;

        ctrl.create = create;
        ctrl.update = update;
        ctrl.searchMembers = searchMembers;
        ctrl.changeMembersPage = changeMembersPage;
        ctrl.changeSessionsPage = changeSessionsPage;
        ctrl.selectMember = selectMember;
        ctrl.selectSession = selectSession;

        ctrl.editedSpeech = {};
        ctrl.editMode = false;

        ctrl.members = [];
        ctrl.sessions = [];

        ctrl.searchMemberTerm = null;
        ctrl.currentMembersPage = null;
        ctrl.currentSessionsPage = null;
        ctrl.selectedMember = null;
        ctrl.selectedSession = null;

        // CHECK PAGE MODE - EDIT || CREATE
        if (localStorageService.get("editedSpeech") != null) {
            ctrl.editedSpeech = localStorageService.get("editedSpeech");
            ctrl.selectedMember = ctrl.editedSpeech.member;
            ctrl.selectedSession = {};
            ctrl.selectedSession.id = ctrl.editedSpeech.plenarySessionId;
            if (ctrl.editedSpeech.sessionDate) {
                var day = ctrl.editedSpeech.sessionDate.substring(0, ctrl.editedSpeech.sessionDate.indexOf('.'));
                var month = ctrl.editedSpeech.sessionDate.substring(ctrl.editedSpeech.sessionDate.indexOf('.') + 1, ctrl.editedSpeech.sessionDate.lastIndexOf('.'));
                var year = ctrl.editedSpeech.sessionDate.substring(ctrl.editedSpeech.sessionDate.lastIndexOf('.') + 1, ctrl.editedSpeech.sessionDate.length);
                ctrl.editedSpeech.sessionDate = new Date(year, month - 1, day);
            }
            ctrl.editMode = true;
        }

        //SEARCH MEMBERS
        function searchMembers() {
            ctrl.currentMembersPage = 1;
            listMembers();
        }

        //GET ALL MEMBERS
        function listMembers() {
            Members.getList({page: ctrl.currentMembersPage, query: ctrl.searchMemberTerm}).then(
                function (members) {
                    ctrl.members = members;
                },
                function (response) {
                    dialogs.notify("Error!", response.data.error, null);
                });
        }

        //CHANGE MEMBERS PAGE
        function changeMembersPage(page) {
            ctrl.currentMembersPage = page;
            listMembers();
        }

        //CHANGE SESSIONS PAGE
        function changeSessionsPage(page) {
            ctrl.currentSessionsPage = page;
            listSessions();
        }

        //GET ALL SESSIONS
        function listSessions() {
            Sessions.getList({page: ctrl.currentSessionsPage}).then(
                function (sessions) {
                    ctrl.sessions = sessions;
                },
                function (response) {
                    dialogs.notify("Error!", response.data.error, null);
                });
        }

        // CHECK PAGE MODE - EDIT || CREATE
        if (localStorageService.get("editedSpeech") != null) {
            ctrl.editedSpeech = localStorageService.get("editedSpeech");
            ctrl.editMode = true;
        }

        //CREATE
        function create() {
            if (!ctrl.selectedMember) {
                dialogs.notify("Error!", "Select member!", {size: "md"});
                return;
            }

            if (!ctrl.selectedSession) {
                dialogs.notify("Error!", "Select session!", {size: "md"});
                return;
            }

            if (!ctrl.editedSpeech.text) {
                dialogs.notify("Error!", "Insert speech text!", {size: "md"});
                return;
            }

            ctrl.editedSpeech.member = {};
            ctrl.editedSpeech.member.id = ctrl.selectedMember.id;
            ctrl.editedSpeech.plenarySessionId = ctrl.selectedSession.id;
            ctrl.editedSpeech.sessionDate = parseStringSessionIntoDate(ctrl.selectedSession);

            Speeches.post(ctrl.editedSpeech).then(
                function (response) {
                    ctrl.editedSpeech = {};
                    ctrl.selectedMember = null;
                    ctrl.selectedSession = null;
                    dialogs.notify("Success!", "Speech successfully created!", {size: "md"});
                },
                function (response) {
                    dialogs.notify("Error!", 'Status: ' + response.status + ' Message: ' + response.data.error, {size: "md"});
                });
        }

        //UPDATE
        function update() {
            ctrl.putSpeech = Restangular.copy(ctrl.editedSpeech);
            ctrl.putSpeech.put().then(
                function (speech) {
                    ctrl.editedSpeech = speech;
                    dialogs.notify("Success!", "Speech successfully updated!", {size: "md"});
                },
                function (response) {
                    dialogs.notify("Error!", 'Status: ' + response.status + ' Message: ' + response.data.error, {size: "md"});
                })
        }

        function selectMember(member) {
            ctrl.selectedMember = member;
        }

        function selectSession(session) {
            ctrl.selectedSession = session;
        }

        function parseStringSessionIntoDate(session) {
            if (session.date) {
                var day = session.date.substring(0, session.date.indexOf('.'));
                var month = session.date.substring(session.date.indexOf('.') + 1, session.date.lastIndexOf('.'));
                var year = session.date.substring(session.date.lastIndexOf('.') + 1, session.date.length);
                return new Date(year, month - 1, day);
            }
        }

    }
};
