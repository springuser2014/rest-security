/*
* jQuery Mobile Table Plugin v0.1.0 (2013-12-23)
*
* Copyright (c) 2013 zzh
 API

 10. Destroy
 $('#table').jqueryMobileTable('destroy');

*/
(function($) {

    "use strict";

        /**
         * Plugin Constructor. Every menu must have a unique id which will either
         * be the actual id attribute or its index in the page.
         *
         * @param {Element} el
         * @param {Object} options
         * @param {Integer} idx
         * @returns {Object} Plugin Instance
         */
    var Plugin = function(el, options, idx) {
        //console.log('Plugin');
        this.el = el;
        this.$el = $(el);
        this.options = options;
        this.uuid = this.$el.attr('id') ? this.$el.attr('id') : idx;
        this.bDiv = undefined,
        this.tcDiv = undefined,
        this.state = {};
        this.init();
        this.loading = false;
        return this;
    };
    
    /**
     * Plugin methods
     */
     Plugin.prototype = {
        /**
         * 
         */
         init: function() {
            //console.log('init');
            var self = this;

            if(self.el.tagName.toUpperCase()!='TABLE') {
                alert("Element is not a table");
                return;
            }
            var i, dir;
            if(self.options.colNames.length === 0) {
                for (i=0;i<self.options.colModel.length;i++){
                    this.options.colNames[i] = self.options.colModel[i].label || self.options.colModel[i].name;
                }
            }
            if(self.options.colNames.length !== self.options.colModel.length) {
                alert($.jgrid.errors.model);
                return;
            }

            if(self.options.multiselect) {
                self.options.colNames.unshift("<input role='checkbox' id='cb_"+self.options.id+"' class='cbox' type='checkbox'/>");
                self.options.colModel.unshift({name:'cb',sortable:false,resizable:false,hidedlg:true,search:false,align:'center',fixed:true});
            }
            if(self.options.rownumbers) {
                self.options.colNames.unshift("");
                self.options.colModel.unshift({name:'rn',sortable:false,resizable:false,hidedlg:true,search:false,align:'center',fixed:true});
            }
            
            this.options.xmlReader = $.extend(true,{
                root: "rows",
                row: "row",
                page: "rows>page",
                total: "rows>total",
                records : "rows>records",
                repeatitems: true,
                cell: "cell",
                id: "[id]",
                userdata: "userdata",
                subgrid: {root:"rows", row: "row", repeatitems: true, cell:"cell"}
            }, self.options.xmlReader);
            this.options.jsonReader = $.extend(true,{
                root: "rows",
                page: "page",
                total: "total",
                records: "records",
                repeatitems: true,
                cell: "cell",
                id: "id",
                userdata: "userdata",
                subgrid: {root:"rows", repeatitems: true, cell:"cell"}
            },self.options.jsonReader);
            this.options.localReader = $.extend(true,{
                root: "rows",
                page: "page",
                total: "total",
                records: "records",
                repeatitems: false,
                cell: "cell",
                id: "id",
                userdata: "userdata",
                subgrid: {root:"rows", repeatitems: true, cell:"cell"}
            },self.options.localReader);

            if(self.options.grouping) {
            }

            var tr = $('<tr>').addClass('ui-flipswitch-active');

            for(i=0;i<self.options.colNames.length;i++){
                var tooltip = self.options.headertitles ? (' title=\"'+stripHtml(self.options.colNames[i])+'\"') :'',
                    proiority = self.options.colModel[i].priority == undefined || self.options.colModel[i].priority == '' || 
                         self.options.colModel[i].priority < 1 || self.options.colModel[i].priority > 6
                          ? 1 : self.options.colModel[i].priority;
                if(this.options.rownumbers && i == 0) {
                    proiority = 3;
                 }
                this.options.colPrioritys[i] = proiority;

                $('<th>')
                .css({
                    'display':self.options.colModel[i].hidden ? 'none' : ''
                })
                .html(self.options.colNames[i])
                .attr({
                    title: self.options.headertitles ? stripHtml(self.options.colNames[i]) :"",
                    'data-colstart': i+1,
                    'data-priority': proiority
                })
                .addClass('ui-table-priority-'+proiority)
                .appendTo(tr);
            }
            
            this.$el
            //.attr({'data-role': 'table'})
            .addClass('ui-responsive table-stroke ui-table ui-table-columntoggle')
            .bind('reloadGrid', function(e,opts) {
                //console.log('reloadGrid');
                if(self.options.treeGrid ===true) { self.options.datatype = self.options.treedatatype;}
                if (opts && opts.current) {
                    self.selectionPreserver(self);
                }
                if(self.options.datatype=="local"){ self.resetSelection();  if(self.options.data.length) { refreshIndex();} }
                else if(!self.options.treeGrid) {
                    self.options.selrow=null;
                    //if(self.options.multiselect) {self.options.selarrrow =[];setHeadCheckBox(false);}
                    self.options.savedRow = [];
                }
                if(self.options.scroll) {emptyRows.call(self.$el, true, false);}
                if (opts && opself.optionsage) {
                    var page = opself.optionsage;
                    if (page > self.options.lastpage) { page = self.options.lastpage; }
                    if (page < 1) { page = 1; }
                    self.options.page = page;
                }
                self.populate();
                //if(self.options._inlinenav===true) {self.showAddEditButtons();}
                return false;
            });

            var bDiv = $('<div>'),
                tc = $('<div>').attr({'id': self.uidPref+'_'+self.uuid});

            if(self.options.caption != "") {
                $('<div>')
                .addClass('ui-header ui-bar-inherit')
                .append($('<span class="ui-title">'+self.options.caption+'</span>'))
                .appendTo(tc);
            }

            $('<thead>')
            .append(tr)
            .appendTo(this.$el);

            //$(tc).insertBefore(this.$el);

            bDiv.insertBefore(this.$el);

            this.$el
            .attr({'id': self.uuid})
            .appendTo(bDiv);

            // loader
            $('<div>')
            .addClass('ui-loader ui-corner-all ui-body-a ui-loader-verbose')
            .append($('<span class="ui-icon-loading"></span><h1>'+$.jgrid.defaults.loadtext+'</h1>'))
            .appendTo(bDiv);

            $(tc).insertBefore(bDiv);
            bDiv.appendTo(tc);

            this.bDiv = bDiv;
            this.tcDiv = tc;

            self.populate();

            //self._load();
        },
        populate: function (npage) {
            //console.log('06populate');
            var self = this;
            if(!this.loading) {
                var prm = {}, dt, dstr, pN=this.options.prmNames;

                if(this.options.page <=0) { this.options.page = 1; }
                if(pN.search !== null) {prm[pN.search] = this.options.search;}
                if(pN.nd !== null) {prm[pN.nd] = new Date().getTime();}
                if(pN.rows !== null) {prm[pN.rows]= this.options.rowNum;}
                if(pN.page !== null) {prm[pN.page]= this.options.page;}
                if(pN.sort !== null) {prm[pN.sort]= this.options.sortname;}
                if(pN.order !== null) {prm[pN.order]= this.options.sortorder;}
                if(this.options.rowTotal !== null && pN.totalrows !== null) { prm[pN.totalrows]= this.options.rowTotal; }

                var lcf = $.isFunction(this.options.loadComplete), lc = lcf ? this.options.loadComplete : null;
                var adjust = 0;
                npage = npage || 1;

                if (npage > 1) {
                    prm[pN.page] = npage;
                    if(pN.npage !== null) {
                        prm[pN.npage] = npage;
                        adjust = npage - 1;
                        npage = 1;
                    } else {
                        lc = function(req) {
                            self.options.page++;
                            self.loading = false;
                            if (lcf) {
                                self.options.loadComplete.call(self,req);
                            }
                            self.populate(npage-1);
                        };
                    }
                } else if (pN.npage !== null) {
                    delete this.options.postData[pN.npage];
                }

                prm[pN.npage] = npage;
                $.extend(true,this.options.postData,prm);

                dt = this.options.datatype.toLowerCase();
                switch(dt)
                {
                case "json":
                case "jsonp":
                case "xml":
                case "script":
                    $.ajax($.extend({
                        async: true,
                        url:this.options.url,
                        type:this.options.mtype,
                        dataType: dt ,
                        data: $.isFunction(this.options.serializeGridData)? this.options.serializeGridData.call(this,this.options.postData) : this.options.postData,
                        success:function(data, st, xhr) {
                            if ($.isFunction(self.options.beforeProcessing)) {
                                if (self.options.beforeProcessing.call(self, data, st, xhr) === false) {
                                    self.endReq();
                                    return;
                                }
                            }
                            if(dt === "json") { self.addJSONData(data, npage>1, adjust); }
                            if(lc) { lc.call(this,data); }
                            if( self.options.loadonce || self.options.treeGrid) {self.options.datatype = "local";}
                            data=null;
                            self.endReq();
                        },
                        error:function(xhr,st,err){
                            if($.isFunction(self.options.loadError)) { self.options.loadError.call(self,xhr,st,err); }
                            self.endReq();
                            xhr=null;
                        },
                        beforeSend: function(xhr, settings ){
                            var gotoreq = true;
                            if($.isFunction(self.options.loadBeforeSend)) {
                                gotoreq = self.options.loadBeforeSend.call(self, xhr, settings); 
                            }
                            if(gotoreq === undefined) { gotoreq = true; }
                            if(gotoreq === false) {
                                return false;
                            } else {
                                self.beginReq();
                            }
                        }
                    }, self.ajaxOptions, this.options.ajaxGridOptions));
                break;
                case "xmlstring":
                break;
                case "jsonstring":
                break;
                case "local":
                case "clientside":
                break;
                }
            }
        },
        addJSONData: function(data, t, more, adjust) {
            //console.log('addJSONData' + '_' + this.uuid);
            var self = this;
            var startReq = new Date();

            var dReader, locid = "_id_", frd, tbody = $('<tbody>'),
            locdata = (this.options.datatype != "local" && this.options.loadonce) || this.options.datatype == "jsonstring";
            if(locdata) { this.options.data = []; this.options._index = {}; this.options.localReader.id = locid;}
            this.options.reccount = 0;
            if(this.options.datatype == "local") {
                dReader =  this.options.localReader;
                frd= 'local';
            } else {
                dReader =  this.options.jsonReader;
                frd='json';
            }
            var ir=0,v,i,j,f=[],F,cur,gi=this.options.multiselect?1:0,
                si=this.options.subGrid?1:0,ni=this.options.rownumbers===true?1:0,
                len,drows,idn,rd={}, fpos, idr,rowData=[],
                cn=(this.options.altRows === true) ? " "+this.options.altclass:"",
                cn1,lp;
                this.options.page = this.getAccessor(data,dReader.page) || this.options.page || 0;
            
            lp = this.getAccessor(data,dReader.total);
            this.options.lastpage = lp === undefined ? 1 : lp;
            this.options.records = this.getAccessor(data,dReader.records) || 0;
            this.options.userData = this.getAccessor(data,dReader.userdata) || {};


            drows = this.getAccessor(data,dReader.root);
            if (!drows) { drows = []; }
            len = drows.length; i=0;
            if (len > 0 && this.options.page <= 0) { this.options.page = 1; }
            var rn = parseInt(this.options.rowNum,10),br=this.options.scroll ? randId() : 1, altr;
            if (adjust) { rn *= adjust+1; }

            while (i<len) {
                var rowid = drows[i].id;
                var trow = $('<tr>')
                .attr('id',self.uidPref+'_'+self.uuid+'_'+rowid)
                .click(function(){
                    var data_id = $(this).attr('id').replace(self.uidPref+'_'+self.uuid+'_','');
                    tbody.find('tr').removeClass('ui-flipswitch-active');
                    $(this).addClass('ui-flipswitch-active');
                    self.options.onSelectRow(data_id) || {};
                });
                cur = drows[i];
                idr = this.getAccessor(cur,idn);
                if(idr === undefined) {
                    idr = br+i;
                    if(f.length===0){
                        if(dReader.cell){
                            var ccur = this.getAccessor(cur,dReader.cell);
                            idr = ccur !== undefined ? ccur[idn] || idr : idr;
                            ccur=null;
                        }
                    }
                }
                idr  = this.options.idPrefix + idr - ni;
                //altr = rcnt === 1 ? 0 : rcnt;
                cn1 = (altr+i)%2 == 1 ? cn : '';
                var iStartTrTag = rowData.length;
                
                if( ni ) {
                    trow.append( this.addRowNum(0,i,this.options.page,this.options.rowNum, this.options.colPrioritys[0]) );
                }
                if (dReader.repeatitems) {
                    if(dReader.cell) {cur = this.getAccessor(cur,dReader.cell);}
                    if (!F) { F=this.orderedCols(gi+si+ni); }
                }
                for (j=0;j<F.length;j++) {
                    v = this.getAccessor(cur,F[j]);
                    //rd[this.options.colModel[j+gi+si+ni].name] = v;
                    trow.append( this.addCell(idr,v,j+gi+si+ni,i,cur, rd, this.options.colPrioritys[j+gi+si+ni]) );
                }
                tbody.append(trow);

                rd={};
                ir++;
                i++;
                if(ir==rn) { break; }
            }
            
            this.$el
            .find('tbody')
            .remove();
            
            tbody.appendTo(this.$el);
            
            if(locdata) {
                this.options.records = len;
                this.options.lastpage = Math.ceil(len/ rn);
            }
            if (!more) { this.updatepager(false,true); }
        },
        getRowData : function( rowid ) {
            //console.log('getRowData');
            if(rowid == undefined) return {};
            var self = this,res = {}, resall, getall=false, len, j=0;
            var $tR = $('#' + self.uidPref+'_'+self.uuid+'_'+rowid),nm,ind;
                len = self.options.colModel.length;
                while(j<len){
                    var index = self.options.colModel[j].index;
                    var data = $tR.find('td[aria-describedby='+index+']').text();
                    res[index] = data;
                    j++;
                }
            return res;
        },
        selectionPreserver : function(ts) {
            //console.log('selectionPreserver');
            var p = ts.options,
            sr = p.selrow, sra = p.selarrrow ? $.makeArray(p.selarrrow) : null,
            left = ts.grid.bDiv.scrollLeft,
            restoreSelection = function() {
                var i;
                p.selrow = null;
                p.selarrrow = [];
                if(p.multiselect && sra && sra.length>0) {
                    for(i=0;i<sra.length;i++){
                        if (sra[i] != sr) {
                            $(ts).jqGrid("setSelection",sra[i],false, null);
                        }
                    }
                }
                if (sr) {
                    $(ts).jqGrid("setSelection",sr,false,null);
                }
                ts.grid.bDiv.scrollLeft = left;
                $(ts).unbind('.selectionPreserver', restoreSelection);
            };
            $(ts).bind('jqGridGridComplete.selectionPreserver', restoreSelection);              
        },
        beginReq: function() {
            this.loading = true;
            if(this.options.hiddengrid) { return;}
            switch(this.options.loadui) {
                case "disable":
                    break;
                case "enable":
                    this.bDiv.addClass('ui-loading');
                    break;
                case "block":
                    this.bDiv.addClass('ui-loading');
                    break;
            }
        },
        endReq: function() {
            this.loading = false;
            switch(this.options.loadui) {
                case "disable":
                    break;
                case "enable":
                    this.bDiv.removeClass('ui-loading');
                    break;
                case "block":
                    this.bDiv.removeClass('ui-loading');
                    break;
            }
        },
        jqID: function(sid){
            return String(sid).replace(/[!"#$%&'()*+,.\/:;<=>?@\[\\\]\^`{|}~]/g,"\\$&");
        },
        guid : 1,
        uidPref: 'jqmT',
        randId : function( prefix ) {
            return (prefix? prefix: this.uidPref) + (this.guid++);
        },
        getAccessor: function(obj, expr) {
            var ret,p,prm = [], i;
            if( typeof expr === 'function') { return expr(obj); }
            ret = obj[expr];
            if(ret===undefined) {
                try {
                    if ( typeof expr === 'string' ) {
                        prm = expr.split('.');
                    }
                    i = prm.length;
                    if( i ) {
                        ret = obj;
                        while (ret && i--) {
                            p = prm.shift();
                            ret = ret[p];
                        }
                    }
                } catch (e) {}
            }
            return ret;
        },
        intNum: function(val,defval) {
            val = parseInt(val,10);
            if (isNaN(val)) { return defval ? defval : 0;}
            else {return val;}
        },
        formatCol: function (pos, rowInd, tv, rawObject, rowId, rdata){
            var cm = this.options.colModel[pos],
            ral = cm.align, result="style=\"", clas = cm.classes, nm = cm.name, celp, acp=[];
            if(ral) { result += "text-align:"+ral+";"; }
            if(cm.hidden===true) { result += "display:none;"; }
            if(rowInd===0) {
                result += "";
            } else if (cm.cellattr && $.isFunction(cm.cellattr))
            {
                celp = cm.cellattr.call(ts, rowId, tv, rawObject, cm, rdata);
                if(celp && typeof(celp) === "string") {
                    celp = celp.replace(/style/i,'style').replace(/title/i,'title');
                    if(celp.indexOf('title') > -1) { cm.title=false;}
                    if(celp.indexOf('class') > -1) { clas = undefined;}
                    acp = celp.split("style");
                    if(acp.length === 2 ) {
                        acp[1] =  $.trim(acp[1].replace("=",""));
                        if(acp[1].indexOf("'") === 0 || acp[1].indexOf('"') === 0) {
                            acp[1] = acp[1].substring(1);
                              }
                        result += acp[1].replace(/'/gi,'"');
                    } else {
                        result += "\"";
                    }
                }
            }
            if(!acp.length) { acp[0] = ""; result += "\"";}
            result += (clas !== undefined ? (" class=\""+clas+"\"") :"") + ((cm.title && tv) ? (" title=\""+$.jgrid.stripHtml(tv)+"\"") :"");
            result += " aria-describedby=\""+nm+"\"";
            return result + acp[0];
        },
        cellVal:  function (val) {
            return val === undefined || val === null || val === "" ? "&#160;" : (this.options.autoencode ? $.jgrid.htmlEncode(val) : val+"");
        },
        formatter: function (rowId, cellval , colpos, rwdat, _act){
            var ts = this,cm = this.options.colModel[colpos],v;
            if(typeof cm.formatter !== 'undefined') {
                var opts= {rowId: rowId, colModel:cm, gid:this.options.id, pos:colpos };
                if($.isFunction( cm.formatter ) ) {
                    v = cm.formatter.call(ts,cellval,opts,rwdat,_act);
                } else if($.fmatter){
                    v = $.fn.fmatter.call(ts,cm.formatter,cellval,opts,rwdat,_act);
                } else {
                    v = this.cellVal(cellval);
                }
            } else {
                v = this.cellVal(cellval);
            }
            return v;
        },
        addCell: function(rowId,cell,pos,irow, srvr, rdata, pri) {
            var v,prp;
            v = this.formatter(rowId,cell,pos,srvr,'add');
            prp = this.formatCol( pos,irow, v, srvr, rowId, rdata);
            return $('<td class="ui-table-priority-' + pri + '" '+prp+'>'+v+'</td>');
        },
        addRowNum: function (pos,irow,pG,rN, pri) {
            var v =  (parseInt(pG,10)-1)*parseInt(rN,10)+1+irow;
            //prp = this.formatCol( pos,irow,v, null, irow, true);
            return $('<th class="ui-table-priority-' + pri + '">'+v+'</th>');
        },
        orderedCols: function (offset) {
            var order = this.options.remapColumns;
            if (!order || !order.length) {
                order = $.map(this.options.colModel, function(v,i) { return i; });
            }
            if (offset) {
                order = $.map(order, function(v) { return v<offset?null:v-offset; });
            }
            return order;
        },
        stripHtml: function(v) {
            v = v+"";
            var regexp = /<("[^"]*"|'[^']*'|[^'">])*>/gi;
            if (v) {
                v = v.replace(regexp,"");
                return (v && v !== '&nbsp;' && v !== '&#160;') ? v.replace(/\"/g,"'") : "";
            } else {
                return v;
            }
        },
        constructTr: function(id, hide, altClass, rd, cur) {
            var tabindex = '-1', restAttr = '', attrName, style = hide ? 'display:none;' : '',
                classes = 'jqgrow ui-row-' + self.options.direction + altClass,
                rowAttrObj = $.isFunction(self.options.rowattr) ? self.options.rowattr.call(ts, rd, cur) : {};
            if(!$.isEmptyObject( rowAttrObj )) {
                if (rowAttrObj.hasOwnProperty("id")) {
                    id = rowAttrObj.id;
                    delete rowAttrObj.id;
                }
                if (rowAttrObj.hasOwnProperty("tabindex")) {
                    tabindex = rowAttrObj.tabindex;
                    delete rowAttrObj.tabindex;
                }
                if (rowAttrObj.hasOwnProperty("style")) {
                    style += rowAttrObj.style;
                    delete rowAttrObj.style;
                }
                if (rowAttrObj.hasOwnProperty("class")) {
                    classes += ' ' + rowAttrObj['class'];
                    delete rowAttrObj['class'];
                }
                // dot't allow to change role attribute
                try { delete rowAttrObj.role; } catch(ra){}
                for (attrName in rowAttrObj) {
                    if (rowAttrObj.hasOwnProperty(attrName)) {
                        restAttr += ' ' + attrName + '=' + rowAttrObj[attrName];
                    }
                }
            }
            return '<tr role="row" id="' + id + '" tabindex="' + tabindex + '" class="' + classes + '"' +
                (style === '' ? '' : ' style="' + style + '"') + restAttr + '>';
        },
        footerData: function(action,data, format) {
            var nm, success=false, res={}, title, self = this;
            function isEmpty(obj) {
                for(var i in obj) {
                    if (obj.hasOwnProperty(i)) { return false; }
                }
                return true;
            }
            if(typeof(action) == "undefined") { action = "get"; }
            if(typeof(format) != "boolean") { format  = true; }
            action = action.toLowerCase();
            this.each(function(){
                var t = this, vl;
                if(!t.grid || !t.p.footerrow) {return false;}
                if(action == "set") { if(isEmpty(data)) { return false; } }
                success=true;
                $(self.options.colModel).each(function(i){
                    nm = this.name;
                    if(action == "set") {
                        if( data[nm] !== undefined) {
                            vl = format ? t.formatter( "", data[nm], i, data, 'edit') : data[nm];
                            title = this.title ? {"title":$.jgrid.stripHtml(vl)} : {};
                            $("tr.footrow td:eq("+i+")",t.grid.sDiv).html(vl).attr(title);
                            success = true;
                        }
                    } else if(action == "get") {
                        res[nm] = $("tr.footrow td:eq("+i+")",t.grid.sDiv).html();
                    }
                });
            });
            return action == "get" ? res : success;
        },
        updatepager: function(rn, dnd) {
            var self = this, ts = $(this.options.pager), pu,start, end,
            pu = $('<ul>').addClass('pagination');
            
            //首页
            var first = $('<a>')
            .attr({
                'data-pager': 1,
                'href': '#'
            })
            .html('&laquo;')
            .click(function(){
                var pg = $(this).attr('data-pager');
                if(self.options.page == 1) {
                    return false;
                }
                self.options.page = pg;
                self.populate();
                return false;
            });

            $('<li>')
            .addClass(self.options.page == 1 ? 'disabled' : '')
            .append(first)
            .appendTo(pu);
            
            //上一页
            var pre = $('<a></a>')
            .attr({
                'data-pager': parseInt(self.options.page) - 1 >= 1 ? parseInt(self.options.page) - 1 : 1,
                'href': '#'
            })
            .html('&lsaquo;')
            .click(function(){
                var pg = $(this).attr('data-pager');
                if(self.options.page == 1) {
                    return false;
                }
                self.options.page = pg;
                self.populate();
                return false;
            });

            $('<li>')
            .addClass(self.options.page == 1 ? 'disabled' : '')
            .append(pre)
            .appendTo(pu);

            start = parseInt(self.options.page) - 2 >= 1 ? 
                    (parseInt(self.options.page) +2 <= self.options.lastpage ? parseInt(self.options.page) - 2 : (self.options.lastpage - 5 > 0 ? self.options.lastpage - 5 : 1)) : 1;
            end =(parseInt(self.options.page) + 2 <= self.options.lastpage ? 
                    (parseInt(self.options.page) + 2 < 5 ? (self.options.lastpage > 5 ? 5 : self.options.lastpage) : parseInt(self.options.page) + 2) : self.options.lastpage);
            
            for(var i = start; i <= end; i++) {
                 var page = $('<a>')
                .attr({
                    'data-pager': i,
                    'href': '#'
                })
                .html(i)
                .click(function(){
                    var pg = $(this).attr('data-pager');
                    if(pg == self.options.page) {
                        return false;
                    }
                    self.options.page = pg;
                    self.populate();
                    return false;
                });
                $('<li></li>')
                .addClass(i == self.options.page ? 'active' : '')
                .append(page)
                .appendTo(pu);
            }

            //下一页
            var next = $('<a>')
            .attr({
                'data-pager': parseInt(self.options.page) + 1 <= self.options.lastpage ? parseInt(self.options.page) + 1 : self.options.lastpage,
                'href': '#'
            })
            .html('&rsaquo;')
            .click(function(){
                var pg = $(this).attr('data-pager');
                if(self.options.page == self.options.lastpage || self.options.lastpage == 0) {
                    return false;
                }
                self.options.page = pg;
                self.populate();
                return false;
            });

            $('<li>')
            .addClass(self.options.page == self.options.lastpage || self.options.lastpage == 0 ? 'disabled' : '')
            .append(next)
            .appendTo(pu);
                    
            //末页
            var last = $('<a>')
            .attr({
                'data-pager': self.options.lastpage,
                'href': '#'
            })
            .html('&raquo;')
            .click(function(){
                var pg = $(this).attr('data-pager');
                if(self.options.page == self.options.lastpage || self.options.lastpage == 0) {
                    return false;
                }
                self.options.page = pg;
                self.populate();
                return false;
            });

            $('<li>')
            .addClass(self.options.page == self.options.lastpage || self.options.lastpage == 0 ? 'disabled' : '')
            .append(last)
            .appendTo(pu);

            $('#' + this.uidPref+'_'+this.options.pager.replace('#','')).remove();
            
            $('<div>')
            .attr({'id': this.uidPref+'_'+this.options.pager.replace('#','')})
            .addClass('text-center')
            .append(pu)
            .appendTo(ts)
                    
            ts.appendTo(this.tcDiv);
        },
        setGridParam : function (newParams){
            var self = this;
            return this.$el.each(function(){
                if (typeof(newParams) === 'object') {$.extend(true,self.options,newParams);}
            });
        },
        /**
         * If `save` option is on it reads the cookie data. The cookie contains data for all
         * jqueryMobileTable menus so the read happens only once and stored in the global `cookie` var.
         */
         _load: function() {
            //console.log('load');
            if (this.options.save) {
                if (cookie === null) {
                    var data = $.cookie(this.options.cookie.name);
                    cookie = (data) ? JSON.parse(data) : {};
                }
                this.state = cookie.hasOwnProperty(this.uuid) ? cookie[this.uuid] : {};
            }
        },
        /**
         * Removes instance from JQuery data cache and unbinds events.
         */
         destroy: function() {
            //console.log('destroy');
            $.removeData(this.$el);
            this.$el.find("li:has(ul) > a").unbind('click');
        },
        ajaxOptions: {}
    };

    /**
     * format
     */
(function($) {
    "use strict";   
    $.fmatter = {};
    //opts can be id:row id for the row, rowdata:the data for the row, colmodel:the column model for this column
    //example {id:1234,}
    $.extend($.fmatter,{
        isBoolean : function(o) {
            return typeof o === 'boolean';
        },
        isObject : function(o) {
            return (o && (typeof o === 'object' || $.isFunction(o))) || false;
        },
        isString : function(o) {
            return typeof o === 'string';
        },
        isNumber : function(o) {
            return typeof o === 'number' && isFinite(o);
        },
        isNull : function(o) {
            return o === null;
        },
        isUndefined : function(o) {
            return typeof o === 'undefined';
        },
        isValue : function (o) {
            return (this.isObject(o) || this.isString(o) || this.isNumber(o) || this.isBoolean(o));
        },
        isEmpty : function(o) {
            if(!this.isString(o) && this.isValue(o)) {
                return false;
            }else if (!this.isValue(o)){
                return true;
            }
            o = $.trim(o).replace(/\&nbsp\;/ig,'').replace(/\&#160\;/ig,'');
            return o==="";  
        }
    });
    $.fn.fmatter = function(formatType, cellval, opts, rwd, act) {
        // build main options before element iteration
        var v=cellval;
        opts = $.extend({}, $.fmatter, opts);

        try {
            v = $.fn.fmatter[formatType].call(this, cellval, opts, rwd, act);
        } catch(fe){}
        return v;
    };
    $.fmatter.util = {
        // Taken from YAHOO utils
        NumberFormat : function(nData,opts) {
            if(!$.fmatter.isNumber(nData)) {
                nData *= 1;
            }
            if($.fmatter.isNumber(nData)) {
                var bNegative = (nData < 0);
                var sOutput = nData + "";
                var sDecimalSeparator = (opts.decimalSeparator) ? opts.decimalSeparator : ".";
                var nDotIndex;
                if($.fmatter.isNumber(opts.decimalPlaces)) {
                    // Round to the correct decimal place
                    var nDecimalPlaces = opts.decimalPlaces;
                    var nDecimal = Math.pow(10, nDecimalPlaces);
                    sOutput = Math.round(nData*nDecimal)/nDecimal + "";
                    nDotIndex = sOutput.lastIndexOf(".");
                    if(nDecimalPlaces > 0) {
                    // Add the decimal separator
                        if(nDotIndex < 0) {
                            sOutput += sDecimalSeparator;
                            nDotIndex = sOutput.length-1;
                        }
                        // Replace the "."
                        else if(sDecimalSeparator !== "."){
                            sOutput = sOutput.replace(".",sDecimalSeparator);
                        }
                    // Add missing zeros
                        while((sOutput.length - 1 - nDotIndex) < nDecimalPlaces) {
                            sOutput += "0";
                        }
                    }
                }
                if(opts.thousandsSeparator) {
                    var sThousandsSeparator = opts.thousandsSeparator;
                    nDotIndex = sOutput.lastIndexOf(sDecimalSeparator);
                    nDotIndex = (nDotIndex > -1) ? nDotIndex : sOutput.length;
                    var sNewOutput = sOutput.substring(nDotIndex);
                    var nCount = -1;
                    for (var i=nDotIndex; i>0; i--) {
                        nCount++;
                        if ((nCount%3 === 0) && (i !== nDotIndex) && (!bNegative || (i > 1))) {
                            sNewOutput = sThousandsSeparator + sNewOutput;
                        }
                        sNewOutput = sOutput.charAt(i-1) + sNewOutput;
                    }
                    sOutput = sNewOutput;
                }
                // Prepend prefix
                sOutput = (opts.prefix) ? opts.prefix + sOutput : sOutput;
                // Append suffix
                sOutput = (opts.suffix) ? sOutput + opts.suffix : sOutput;
                return sOutput;
                
            } else {
                return nData;
            }
        },
        // Tony Tomov
        // PHP implementation. Sorry not all options are supported.
        // Feel free to add them if you want
        DateFormat : function (format, date, newformat, opts)  {
            var token = /\\.|[dDjlNSwzWFmMntLoYyaABgGhHisueIOPTZcrU]/g,
            timezone = /\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g,
            timezoneClip = /[^-+\dA-Z]/g,
            msDateRegExp = new RegExp("^\/Date\\((([-+])?[0-9]+)(([-+])([0-9]{2})([0-9]{2}))?\\)\/$"),
            msMatch = ((typeof date === 'string') ? date.match(msDateRegExp): null),
            pad = function (value, length) {
                value = String(value);
                length = parseInt(length,10) || 2;
                while (value.length < length)  { value = '0' + value; }
                return value;
            },
            ts = {m : 1, d : 1, y : 1970, h : 0, i : 0, s : 0, u:0},
            timestamp=0, dM, k,hl,
            dateFormat=["i18n"];
            // Internationalization strings
            dateFormat.i18n = {
                dayNames: opts.dayNames,
                monthNames: opts.monthNames
            };
            if( format in opts.masks ) { format = opts.masks[format]; }
            if( !isNaN( date - 0 ) && String(format).toLowerCase() == "u") {
                //Unix timestamp
                timestamp = new Date( parseFloat(date)*1000 );
            } else if(date.constructor === Date) {
                timestamp = date;
                // Microsoft date format support
            } else if( msMatch !== null ) {
                timestamp = new Date(parseInt(msMatch[1], 10));
                if (msMatch[3]) {
                    var offset = Number(msMatch[5]) * 60 + Number(msMatch[6]);
                    offset *= ((msMatch[4] == '-') ? 1 : -1);
                    offset -= timestamp.getTimezoneOffset();
                    timestamp.setTime(Number(Number(timestamp) + (offset * 60 * 1000)));
                }
            } else {
                date = String(date).split(/[\\\/:_;.,\t\T\s-]/);
                format = format.split(/[\\\/:_;.,\t\T\s-]/);
                // parsing for month names
                for(k=0,hl=format.length;k<hl;k++){
                    if(format[k] == 'M') {
                        dM = $.inArray(date[k],dateFormat.i18n.monthNames);
                        if(dM !== -1 && dM < 12){date[k] = dM+1;}
                    }
                    if(format[k] == 'F') {
                        dM = $.inArray(date[k],dateFormat.i18n.monthNames);
                        if(dM !== -1 && dM > 11){date[k] = dM+1-12;}
                    }
                    if(date[k]) {
                        ts[format[k].toLowerCase()] = parseInt(date[k],10);
                    }
                }
                if(ts.f) {ts.m = ts.f;}
                if( ts.m === 0 && ts.y === 0 && ts.d === 0) {
                    return "&#160;" ;
                }
                ts.m = parseInt(ts.m,10)-1;
                var ty = ts.y;
                if (ty >= 70 && ty <= 99) {ts.y = 1900+ts.y;}
                else if (ty >=0 && ty <=69) {ts.y= 2000+ts.y;}
                timestamp = new Date(ts.y, ts.m, ts.d, ts.h, ts.i, ts.s, ts.u);
            }
            
            if( newformat in opts.masks )  {
                newformat = opts.masks[newformat];
            } else if ( !newformat ) {
                newformat = 'Y-m-d';
            }
            var 
                G = timestamp.getHours(),
                i = timestamp.getMinutes(),
                j = timestamp.getDate(),
                n = timestamp.getMonth() + 1,
                o = timestamp.getTimezoneOffset(),
                s = timestamp.getSeconds(),
                u = timestamp.getMilliseconds(),
                w = timestamp.getDay(),
                Y = timestamp.getFullYear(),
                N = (w + 6) % 7 + 1,
                z = (new Date(Y, n - 1, j) - new Date(Y, 0, 1)) / 86400000,
                flags = {
                    // Day
                    d: pad(j),
                    D: dateFormat.i18n.dayNames[w],
                    j: j,
                    l: dateFormat.i18n.dayNames[w + 7],
                    N: N,
                    S: opts.S(j),
                    //j < 11 || j > 13 ? ['st', 'nd', 'rd', 'th'][Math.min((j - 1) % 10, 3)] : 'th',
                    w: w,
                    z: z,
                    // Week
                    W: N < 5 ? Math.floor((z + N - 1) / 7) + 1 : Math.floor((z + N - 1) / 7) || ((new Date(Y - 1, 0, 1).getDay() + 6) % 7 < 4 ? 53 : 52),
                    // Month
                    F: dateFormat.i18n.monthNames[n - 1 + 12],
                    m: pad(n),
                    M: dateFormat.i18n.monthNames[n - 1],
                    n: n,
                    t: '?',
                    // Year
                    L: '?',
                    o: '?',
                    Y: Y,
                    y: String(Y).substring(2),
                    // Time
                    a: G < 12 ? opts.AmPm[0] : opts.AmPm[1],
                    A: G < 12 ? opts.AmPm[2] : opts.AmPm[3],
                    B: '?',
                    g: G % 12 || 12,
                    G: G,
                    h: pad(G % 12 || 12),
                    H: pad(G),
                    i: pad(i),
                    s: pad(s),
                    u: u,
                    // Timezone
                    e: '?',
                    I: '?',
                    O: (o > 0 ? "-" : "+") + pad(Math.floor(Math.abs(o) / 60) * 100 + Math.abs(o) % 60, 4),
                    P: '?',
                    T: (String(timestamp).match(timezone) || [""]).pop().replace(timezoneClip, ""),
                    Z: '?',
                    // Full Date/Time
                    c: '?',
                    r: '?',
                    U: Math.floor(timestamp / 1000)
                };  
            return newformat.replace(token, function ($0) {
                return $0 in flags ? flags[$0] : $0.substring(1);
            });         
        }
    };
    $.fn.fmatter.defaultFormat = function(cellval, opts) {
        return ($.fmatter.isValue(cellval) && cellval!=="" ) ?  cellval : opts.defaultValue ? opts.defaultValue : "&#160;";
    };
    $.fn.fmatter.email = function(cellval, opts) {
        if(!$.fmatter.isEmpty(cellval)) {
            return "<a href=\"mailto:" + cellval + "\">" + cellval + "</a>";
        }else {
            return $.fn.fmatter.defaultFormat(cellval,opts );
        }
    };
    $.fn.fmatter.checkbox =function(cval, opts) {
        var op = $.extend({},opts.checkbox), ds;
        if(opts.colModel !== undefined && !$.fmatter.isUndefined(opts.colModel.formatoptions)) {
            op = $.extend({},op,opts.colModel.formatoptions);
        }
        if(op.disabled===true) {ds = "disabled=\"disabled\"";} else {ds="";}
        if($.fmatter.isEmpty(cval) || $.fmatter.isUndefined(cval) ) {cval = $.fn.fmatter.defaultFormat(cval,op);}
        cval=cval+"";cval=cval.toLowerCase();
        var bchk = cval.search(/(false|0|no|off)/i)<0 ? " checked='checked' " : "";
        var randomid = "jqgridcheck"+ Math.floor(Math.random()*11);
        return "<input data-role=\"none\"  id=\""+randomid+"\" type=\"checkbox\" " + bchk  + " value=\""+ cval+"\" offval=\"no\" "+ds+ "/>";
    };
    $.fn.fmatter.link = function(cellval, opts) {
        var op = {target:opts.target};
        var target = "";
        if(opts.colModel !== undefined && !$.fmatter.isUndefined(opts.colModel.formatoptions)) {
            op = $.extend({},op,opts.colModel.formatoptions);
        }
        if(op.target) {target = 'target=' + op.target;}
        if(!$.fmatter.isEmpty(cellval)) {
            return "<a "+target+" href=\"" + cellval + "\">" + cellval + "</a>";
        }else {
            return $.fn.fmatter.defaultFormat(cellval,opts);
        }
    };
    $.fn.fmatter.showlink = function(cellval, opts) {
        var op = {baseLinkUrl: opts.baseLinkUrl,showAction:opts.showAction, addParam: opts.addParam || "", target: opts.target, idName: opts.idName},
        target = "", idUrl;
        if(opts.colModel !== undefined && !$.fmatter.isUndefined(opts.colModel.formatoptions)) {
            op = $.extend({},op,opts.colModel.formatoptions);
        }
        if(op.target) {target = 'target=' + op.target;}
        idUrl = op.baseLinkUrl+op.showAction + '?'+ op.idName+'='+opts.rowId+op.addParam;
        if($.fmatter.isString(cellval) || $.fmatter.isNumber(cellval)) {    //add this one even if its blank string
            return "<a "+target+" href=\"" + idUrl + "\">" + cellval + "</a>";
        }else {
            return $.fn.fmatter.defaultFormat(cellval,opts);
        }
    };
    $.fn.fmatter.integer = function(cellval, opts) {
        var op = $.extend({},opts.integer);
        if(opts.colModel !== undefined && !$.fmatter.isUndefined(opts.colModel.formatoptions)) {
            op = $.extend({},op,opts.colModel.formatoptions);
        }
        if($.fmatter.isEmpty(cellval)) {
            return op.defaultValue;
        }
        return $.fmatter.util.NumberFormat(cellval,op);
    };
    $.fn.fmatter.number = function (cellval, opts) {
        var op = $.extend({},opts.number);
        if(opts.colModel !== undefined && !$.fmatter.isUndefined(opts.colModel.formatoptions)) {
            op = $.extend({},op,opts.colModel.formatoptions);
        }
        if($.fmatter.isEmpty(cellval)) {
            return op.defaultValue;
        }
        return $.fmatter.util.NumberFormat(cellval,op);
    };
    $.fn.fmatter.currency = function (cellval, opts) {
        var op = $.extend({},opts.currency);
        if(opts.colModel !== undefined && !$.fmatter.isUndefined(opts.colModel.formatoptions)) {
            op = $.extend({},op,opts.colModel.formatoptions);
        }
        if($.fmatter.isEmpty(cellval)) {
            return op.defaultValue;
        }
        return $.fmatter.util.NumberFormat(cellval,op);
    };
    $.fn.fmatter.date = function (cellval, opts, rwd, act) {
        var op = $.extend({},opts.date);
        if(opts.colModel !== undefined && !$.fmatter.isUndefined(opts.colModel.formatoptions)) {
            op = $.extend({},op,opts.colModel.formatoptions);
        }
        if(!op.reformatAfterEdit && act=='edit'){
            return $.fn.fmatter.defaultFormat(cellval, opts);
        } else if(!$.fmatter.isEmpty(cellval)) {
            return  $.fmatter.util.DateFormat(op.srcformat,cellval,op.newformat,op);
        } else {
            return $.fn.fmatter.defaultFormat(cellval, opts);
        }
    };
    $.fn.fmatter.select = function (cellval,opts) {
        // jqGrid specific
        cellval = cellval + "";
        var oSelect = false, ret=[], sep, delim;
        if(!$.fmatter.isUndefined(opts.colModel.formatoptions)){
            oSelect= opts.colModel.formatoptions.value;
            sep = opts.colModel.formatoptions.separator === undefined ? ":" : opts.colModel.formatoptions.separator;
            delim = opts.colModel.formatoptions.delimiter === undefined ? ";" : opts.colModel.formatoptions.delimiter;
        } else if(!$.fmatter.isUndefined(opts.colModel.editoptions)){
            oSelect= opts.colModel.editoptions.value;
            sep = opts.colModel.editoptions.separator === undefined ? ":" : opts.colModel.editoptions.separator;
            delim = opts.colModel.editoptions.delimiter === undefined ? ";" : opts.colModel.editoptions.delimiter;
        }
        if (oSelect) {
            var msl =  opts.colModel.editoptions.multiple === true ? true : false,
            scell = [], sv;
            if(msl) {scell = cellval.split(",");scell = $.map(scell,function(n){return $.trim(n);});}
            if ($.fmatter.isString(oSelect)) {
                // mybe here we can use some caching with care ????
                var so = oSelect.split(delim), j=0;
                for(var i=0; i<so.length;i++){
                    sv = so[i].split(sep);
                    if(sv.length > 2 ) {
                        sv[1] = $.map(sv,function(n,i){if(i>0) {return n;}}).join(sep);
                    }
                    if(msl) {
                        if($.inArray(sv[0],scell)>-1) {
                            ret[j] = sv[1];
                            j++;
                        }
                    } else if($.trim(sv[0])==$.trim(cellval)) {
                        ret[0] = sv[1];
                        break;
                    }
                }
            } else if($.fmatter.isObject(oSelect)) {
                // this is quicker
                if(msl) {
                    ret = $.map(scell, function(n){
                        return oSelect[n];
                    });
                } else {
                    ret[0] = oSelect[cellval] || "";
                }
            }
        }
        cellval = ret.join(", ");
        return  cellval === "" ? $.fn.fmatter.defaultFormat(cellval,opts) : cellval;
    };
    $.fn.fmatter.rowactions = function(rid,gid,act,pos) {
        var op ={
            keys:false,
            onEdit : null, 
            onSuccess: null, 
            afterSave:null,
            onError: null,
            afterRestore: null,
            extraparam: {},
            url: null,
            restoreAfterError: true,
            mtype: "POST",
            delOptions: {},
            editOptions : {}
        };
        rid = $.jgrid.jqID( rid );
        gid = $.jgrid.jqID( gid );
        var cm = $('#'+gid)[0].p.colModel[pos];
        if(!$.fmatter.isUndefined(cm.formatoptions)) {
            op = $.extend(op,cm.formatoptions);
        }
        if( !$.fmatter.isUndefined($('#'+gid)[0].p.editOptions) ) {
            op.editOptions = $('#'+gid)[0].p.editOptions;
        }
        if( !$.fmatter.isUndefined($('#'+gid)[0].p.delOptions) ) {
            op.delOptions = $('#'+gid)[0].p.delOptions;
        }
        var $t = $("#"+gid)[0];
        var saverow = function( rowid, res) {
            if($.isFunction(op.afterSave)) { op.afterSave.call($t, rowid, res); }
            $("tr#"+rid+" div.ui-inline-edit, "+"tr#"+rid+" div.ui-inline-del","#"+gid + ".ui-jqgrid-btable:first").show();
            $("tr#"+rid+" div.ui-inline-save, "+"tr#"+rid+" div.ui-inline-cancel","#"+gid+ ".ui-jqgrid-btable:first").hide();
        },
        restorerow = function( rowid)   {
            if($.isFunction(op.afterRestore) ) { op.afterRestore.call($t, rowid); }
            $("tr#"+rid+" div.ui-inline-edit, "+"tr#"+rid+" div.ui-inline-del","#"+gid+ ".ui-jqgrid-btable:first").show();
            $("tr#"+rid+" div.ui-inline-save, "+"tr#"+rid+" div.ui-inline-cancel","#"+gid+ ".ui-jqgrid-btable:first").hide();
        };
        if( $("#"+rid,"#"+gid).hasClass("jqgrid-new-row") ){
            var opers = $t.p.prmNames,
            oper = opers.oper;
            op.extraparam[oper] = opers.addoper;
        }
        var actop = {
            keys : op.keys,
            oneditfunc: op.onEdit,
            successfunc: op.onSuccess,
            url: op.url,
            extraparam: op.extraparam,
            aftersavefunc: saverow,
            errorfunc: op.onError,
            afterrestorefunc: restorerow,
            restoreAfterError: op.restoreAfterError,
            mtype: op.mtype
        };
        switch(act)
        {
            case 'edit':
                $('#'+gid).jqGrid('editRow', rid, actop);
                $("tr#"+rid+" div.ui-inline-edit, "+"tr#"+rid+" div.ui-inline-del","#"+gid+ ".ui-jqgrid-btable:first").hide();
                $("tr#"+rid+" div.ui-inline-save, "+"tr#"+rid+" div.ui-inline-cancel","#"+gid+ ".ui-jqgrid-btable:first").show();
                $($t).triggerHandler("jqGridAfterGridComplete");
                break;
            case 'save':
                if ( $('#'+gid).jqGrid('saveRow', rid, actop) ) {
                $("tr#"+rid+" div.ui-inline-edit, "+"tr#"+rid+" div.ui-inline-del","#"+gid+ ".ui-jqgrid-btable:first").show();
                $("tr#"+rid+" div.ui-inline-save, "+"tr#"+rid+" div.ui-inline-cancel","#"+gid+ ".ui-jqgrid-btable:first").hide();
                $($t).triggerHandler("jqGridAfterGridComplete");
                }
                break;
            case 'cancel' :
                $('#'+gid).jqGrid('restoreRow',rid, restorerow);
                $("tr#"+rid+" div.ui-inline-edit, "+"tr#"+rid+" div.ui-inline-del","#"+gid+ ".ui-jqgrid-btable:first").show();
                $("tr#"+rid+" div.ui-inline-save, "+"tr#"+rid+" div.ui-inline-cancel","#"+gid+ ".ui-jqgrid-btable:first").hide();
                $($t).triggerHandler("jqGridAfterGridComplete");
                break;
            case 'del':
                $('#'+gid).jqGrid('delGridRow',rid, op.delOptions);
                break;
            case 'formedit':
                $('#'+gid).jqGrid('setSelection',rid);
                $('#'+gid).jqGrid('editGridRow',rid, op.editOptions);
                break;
        }
    };
    $.fn.fmatter.actions = function(cellval,opts) {
        var op ={keys:false, editbutton:true, delbutton:true, editformbutton: false};
        if(!$.fmatter.isUndefined(opts.colModel.formatoptions)) {
            op = $.extend(op,opts.colModel.formatoptions);
        }
        var rowid = opts.rowId, str="",ocl;
        if(typeof(rowid) =='undefined' || $.fmatter.isEmpty(rowid)) {return "";}
        if(op.editformbutton){
            ocl = "onclick=jQuery.fn.fmatter.rowactions('"+rowid+"','"+opts.gid+"','formedit',"+opts.pos+"); onmouseover=jQuery(this).addClass('ui-state-hover'); onmouseout=jQuery(this).removeClass('ui-state-hover'); ";
            str =str+ "<div title='"+$.jgrid.nav.edittitle+"' style='float:left;cursor:pointer;' class='ui-pg-div ui-inline-edit' "+ocl+"><span class='ui-icon ui-icon-pencil'></span></div>";
        } else if(op.editbutton){
            ocl = "onclick=jQuery.fn.fmatter.rowactions('"+rowid+"','"+opts.gid+"','edit',"+opts.pos+"); onmouseover=jQuery(this).addClass('ui-state-hover'); onmouseout=jQuery(this).removeClass('ui-state-hover') ";
            str =str+ "<div title='"+$.jgrid.nav.edittitle+"' style='float:left;cursor:pointer;' class='ui-pg-div ui-inline-edit' "+ocl+"><span class='ui-icon ui-icon-pencil'></span></div>";
        }
        if(op.delbutton) {
            ocl = "onclick=jQuery.fn.fmatter.rowactions('"+rowid+"','"+opts.gid+"','del',"+opts.pos+"); onmouseover=jQuery(this).addClass('ui-state-hover'); onmouseout=jQuery(this).removeClass('ui-state-hover'); ";
            str = str+"<div title='"+$.jgrid.nav.deltitle+"' style='float:left;margin-left:5px;' class='ui-pg-div ui-inline-del' "+ocl+"><span class='ui-icon ui-icon-trash'></span></div>";
        }
        ocl = "onclick=jQuery.fn.fmatter.rowactions('"+rowid+"','"+opts.gid+"','save',"+opts.pos+"); onmouseover=jQuery(this).addClass('ui-state-hover'); onmouseout=jQuery(this).removeClass('ui-state-hover'); ";
        str = str+"<div title='"+$.jgrid.edit.bSubmit+"' style='float:left;display:none' class='ui-pg-div ui-inline-save' "+ocl+"><span class='ui-icon ui-icon-disk'></span></div>";
        ocl = "onclick=jQuery.fn.fmatter.rowactions('"+rowid+"','"+opts.gid+"','cancel',"+opts.pos+"); onmouseover=jQuery(this).addClass('ui-state-hover'); onmouseout=jQuery(this).removeClass('ui-state-hover'); ";
        str = str+"<div title='"+$.jgrid.edit.bCancel+"' style='float:left;display:none;margin-left:5px;' class='ui-pg-div ui-inline-cancel' "+ocl+"><span class='ui-icon ui-icon-cancel'></span></div>";
        return "<div style='margin-left:8px;'>" + str + "</div>";
    };
    $.unformat = function (cellval,options,pos,cnt) {
        // specific for jqGrid only
        var ret, formatType = options.colModel.formatter,
        op =options.colModel.formatoptions || {}, sep,
        re = /([\.\*\_\'\(\)\{\}\+\?\\])/g,
        unformatFunc = options.colModel.unformat||($.fn.fmatter[formatType] && $.fn.fmatter[formatType].unformat);
        if(typeof unformatFunc !== 'undefined' && $.isFunction(unformatFunc) ) {
            ret = unformatFunc.call(this, $(cellval).text(), options, cellval);
        } else if(!$.fmatter.isUndefined(formatType) && $.fmatter.isString(formatType) ) {
            var opts = $.jgrid.formatter || {}, stripTag;
            switch(formatType) {
                case 'integer' :
                    op = $.extend({},opts.integer,op);
                    sep = op.thousandsSeparator.replace(re,"\\$1");
                    stripTag = new RegExp(sep, "g");
                    ret = $(cellval).text().replace(stripTag,'');
                    break;
                case 'number' :
                    op = $.extend({},opts.number,op);
                    sep = op.thousandsSeparator.replace(re,"\\$1");
                    stripTag = new RegExp(sep, "g");
                    ret = $(cellval).text().replace(stripTag,"").replace(op.decimalSeparator,'.');
                    break;
                case 'currency':
                    op = $.extend({},opts.currency,op);
                    sep = op.thousandsSeparator.replace(re,"\\$1");
                    stripTag = new RegExp(sep, "g");
                    ret = $(cellval).text();
                    if (op.prefix && op.prefix.length) {
                        ret = ret.substr(op.prefix.length);
                    }
                    if (op.suffix && op.suffix.length) {
                        ret = ret.substr(0, ret.length - op.suffix.length);
                    }
                    ret = ret.replace(stripTag,'').replace(op.decimalSeparator,'.');
                    break;
                case 'checkbox':
                    var cbv = (options.colModel.editoptions) ? options.colModel.editoptions.value.split(":") : ["Yes","No"];
                    ret = $('input',cellval).is(":checked") ? cbv[0] : cbv[1];
                    break;
                case 'select' :
                    ret = $.unformat.select(cellval,options,pos,cnt);
                    break;
                case 'actions':
                    return "";
                default:
                    ret= $(cellval).text();
            }
        }
        return ret !== undefined ? ret : cnt===true ? $(cellval).text() : $.jgrid.htmlDecode($(cellval).html());
    };
    $.unformat.select = function (cellval,options,pos,cnt) {
        // Spacial case when we have local data and perform a sort
        // cnt is set to true only in sortDataArray
        var ret = [];
        var cell = $(cellval).text();
        if(cnt===true) {return cell;}
        var op = $.extend({}, !$.fmatter.isUndefined(options.colModel.formatoptions) ? options.colModel.formatoptions: options.colModel.editoptions),
        sep = op.separator === undefined ? ":" : op.separator,
        delim = op.delimiter === undefined ? ";" : op.delimiter;
        
        if(op.value){
            var oSelect = op.value,
            msl =  op.multiple === true ? true : false,
            scell = [], sv;
            if(msl) {scell = cell.split(",");scell = $.map(scell,function(n){return $.trim(n);});}
            if ($.fmatter.isString(oSelect)) {
                var so = oSelect.split(delim), j=0;
                for(var i=0; i<so.length;i++){
                    sv = so[i].split(sep);
                    if(sv.length > 2 ) {
                        sv[1] = $.map(sv,function(n,i){if(i>0) {return n;}}).join(sep);
                    }                   
                    if(msl) {
                        if($.inArray(sv[1],scell)>-1) {
                            ret[j] = sv[0];
                            j++;
                        }
                    } else if($.trim(sv[1])==$.trim(cell)) {
                        ret[0] = sv[0];
                        break;
                    }
                }
            } else if($.fmatter.isObject(oSelect) || $.isArray(oSelect) ){
                if(!msl) {scell[0] =  cell;}
                ret = $.map(scell, function(n){
                    var rv;
                    $.each(oSelect, function(i,val){
                        if (val == n) {
                            rv = i;
                            return false;
                        }
                    });
                    if( typeof(rv) != 'undefined' ) {return rv;}
                });
            }
            return ret.join(", ");
        } else {
            return cell || "";
        }
    };
    $.unformat.date = function (cellval, opts) {
        var op = $.jgrid.formatter.date || {};
        if(!$.fmatter.isUndefined(opts.formatoptions)) {
            op = $.extend({},op,opts.formatoptions);
        }       
        if(!$.fmatter.isEmpty(cellval)) {
            return $.fmatter.util.DateFormat(op.newformat,cellval,op.srcformat,op);
        } else {
            return $.fn.fmatter.defaultFormat(cellval, opts);
        }
    };
})(jQuery);
    /**
     * A JQuery plugin wrapper for jqueryMobileTable. It prevents from multiple instances and also handles
     * public methods calls. If we attempt to call a public method on an element that doesn't have
     * a jqueryMobileTable instance, one will be created for it with the default options.
     *
     * @param {Object|String} options
     */
     $.fn.jqueryMobileTable = function(options) {
        //console.log('Call');
        var $this = $(this),
            obj = $this.data('jqueryMobileTable');
        if (typeof options === 'string' && options.charAt(0) !== '_' && options !== 'init') {
            //console.log('typeof');
            
            var callback = true,
            args = Array.prototype.slice.call(arguments, 1);
            
            if(!obj) {
                alert('Plugin uninitialized');
                return;
            } else {
                //console.log('callback');
                
                var fn = obj.getAccessor(obj,options);
                if (!fn) {
                    throw ("No such method: " + options);
                }
                
                return obj[options].apply(obj, args);
            }
        } else {
            //console.log('options');
            options = $.extend({}, $.fn.jqueryMobileTable.defaults, options || {});
            
            return this.each(function(idx) {

                if(!obj) {
                    //console.log('obj');
                    obj = new Plugin(this, options, idx);
                    $this.data('jqueryMobileTable', obj);
                }
            });
        }
    };

    /**
     * Default jqueryMobileTable options
     *
     * @type {Object}
     */
     $.fn.jqueryMobileTable.defaults = {
        url: "",
        height: 150,
        page: 1,
        rowNum: 20,
        rowTotal : null,
        records: 0,
        pager: "",
        pgbuttons: true,
        pginput: true,
        colModel: [],
        rowList: [],
        colNames: [],
        colPrioritys: [],
        sortorder: "asc",
        sortname: "",
        datatype: "xml",
        mtype: "GET",
        altRows: false,
        selarrrow: [],
        savedRow: [],
        shrinkToFit: true,
        xmlReader: {},
        jsonReader: {},
        subGrid: false,
        subGridModel :[],
        reccount: 0,
        lastpage: 0,
        lastsort: 0,
        selrow: null,
        beforeSelectRow: null,
        onSelectRow: null,
        onSortCol: null,
        ondblClickRow: null,
        onRightClickRow: null,
        onPaging: null,
        onSelectAll: null,
        loadComplete: null,
        gridComplete: null,
        loadError: null,
        loadBeforeSend: null,
        afterInsertRow: null,
        beforeRequest: null,
        beforeProcessing : null,
        onHeaderClick: null,
        viewrecords: false,
        loadonce: false,
        multiselect: false,
        multikey: false,
        editurl: null,
        search: false,
        caption: "",
        hidegrid: true,
        hiddengrid: false,
        postData: {},
        userData: {},
        treeGrid : false,
        treeGridModel : 'nested',
        treeReader : {},
        treeANode : -1,
        ExpandColumn: null,
        tree_root_level : 0,
        prmNames: {page:"page",rows:"rows", sort: "sidx",order: "sord", search:"_search", nd:"nd", id:"id",oper:"oper",editoper:"edit",addoper:"add",deloper:"del", subgridid:"id", npage: null, totalrows:"totalrows"},
        forceFit : false,
        gridstate : "visible",
        cellEdit: false,
        cellsubmit: "remote",
        nv:0,
        loadui: "enable",
        toolbar: [false,""],
        scroll: false,
        multiboxonly : false,
        deselectAfterSort : true,
        scrollrows : false,
        autowidth: false,
        scrollOffset :18,
        cellLayout: 5,
        subGridWidth: 20,
        multiselectWidth: 20,
        gridview: false,
        rownumWidth: 25,
        rownumbers : false,
        pagerpos: 'center',
        recordpos: 'right',
        footerrow : false,
        userDataOnFooter : false,
        hoverrows : true,
        altclass : 'ui-priority-secondary',
        viewsortcols : [false,'vertical',true],
        resizeclass : '',
        autoencode : false,
        remapColumns : [],
        ajaxGridOptions :{},
        direction : "ltr",
        toppager: false,
        headertitles: false,
        scrollTimeout: 40,
        data : [],
        _index : {},
        grouping : false,
        groupingView : {groupField:[],groupOrder:[], groupText:[],groupColumnShow:[],groupSummary:[], showSummaryOnHide: false, sortitems:[], sortnames:[], summary:[],summaryval:[], plusicon: 'ui-icon-circlesmall-plus', minusicon: 'ui-icon-circlesmall-minus'},
        ignoreCase : false,
        cmTemplate : {},
        idPrefix : ""
    };
})(jQuery);