$(function() {
	$('[rel="tooltip"]').tooltip();
});

$(".operacao-de-risco").click(function (e) {
        var resultado = confirm("Tem certeza que deseja realizar esta operação?");
        if (resultado == false) {
            e.preventDefault();
        }
    });

