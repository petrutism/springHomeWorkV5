$(function(){
    $('#language-switcher').change(function(){
        const lang=$('#language-switcher').val();

        if(lang && lang.length > 0){
            window.location.replace(`?lang=${lang}`)
        }
    });
})