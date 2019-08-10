$.fn.serializeObject = function()   {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        var value = this.value;
        var paths = this.name.split(".");
        var len = paths.length;
        var obj = o;
        $.each(paths,function(i,e){
            if(i == len-1){
                if (obj[e]) {
                    if (!obj[e].push) {
                        obj[e] = [obj[e]];
                    }
                    obj[e].push(value || '');
                } else {
                    obj[e] = value || '';
                }
            }else{
                if(!obj[e]){
                    obj[e] = {};
                }
            }
            obj = o[e];
        });
    });
    return o;
}