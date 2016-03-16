$(document).ready(function() {
            $("#question").hide();

            var initForm = $('form');
            initForm.submit(function(event) {
                var age = $("#age").val();
                var gender = $("#gender").val();
                $.ajax({
                    type: initForm.attr('method'),
                    url: initForm.attr('action'),
                    data: 'age=' + age + '&gender=' + gender
                }).done(function() {
                    $('#results').html('Age set as ' + age + ' and gender set as ' +
                        gender + '.').attr('class', 'alert alert-success');
                    $("#personal-info").hide();
                    $("#question").show();
                }).fail(function() {
                    $('#results').html('Unable to connect to server...').attr('class', 'alert alert-danger');
                });
                event.preventDefault();
            });

        });