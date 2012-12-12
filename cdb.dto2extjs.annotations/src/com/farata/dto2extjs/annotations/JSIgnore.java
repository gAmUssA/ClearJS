/**
 * Copyright (c) 2009 Farata Systems  http://www.faratasystems.com
 *
 * Licensed under The MIT License
 * Re-distributions of files must retain the above copyright notice.
 *
 * @license http://www.opensource.org/licenses/mit-license.php The MIT License
 *
 */
package com.farata.dto2extjs.annotations;

import java.lang.annotation.Documented;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * Is preventing translation of the Java property into a corresponding Ext JS model field.
 * Should be used only on a property of the class annotated with <a href="http://help.faratasystems.com/en_US/cleartoolkit/reference/java/extjs/com/farata/dto2extjs/annotations/JSClass.html">&#64;JSClass</a>.
 * <p>In the current implementation you should place &#64;JSIgnore on either getter or setter: annotating
 * a public variable with &#64;JSIgnore will have no effect.</p>
 * </p>
 * <p>As a reminder, only public non-transient properties get translated in the first place.
 * </p>
 * <p>Here is an example of the Java DTO class that attempts to &#64;JSIgnore two out of three properties:
 * <pre>
 * package com.farata.test.dto;
 * import com.farata.dto2extjs.annotations.JSClass;
 * import com.farata.dto2extjs.annotations.JSIgnore;
 * &#64;JSClass(kind=JSClassKind.EXT_JS)
package clear.dto;

import java.util.Date;
import java.util.List;

import com.farata.dto2extjs.annotations.JSClass;
import com.farata.dto2extjs.annotations.JSIgnore;
import com.farata.dto2extjs.annotations.JSOneToMany;

&#64;JSClass
public class UserDTO {
	public String id;
	&#64;JSIgnore   // This annotation is useless
	public Double salary;

	private Date dob;
	&#64;JSIgnore  // This annotation will remove dob from translated fields
	public Date getDob() {
		return dob;
	}
	public void setDob(Date value) {
		dob = value;
	}	
}
 * </pre>
 * The translated Ext JS model is presented below. It assumes that APT parameter  
 * -Acom.faratasystems.dto2extjs.class-name-transformer is AM.model.$1$3<<((\w+\.)*)dto.(\w+)DTO$ 
 * which, in particular, absolves the model name from the DTO suffix. Note that only one property
 * got ignored:
 * <pre>
//Generated by DTO2EXTJS from clear.dto.UserDTO.java on 2012-02-17T21:52:34-05:00
Ext.define('AM.model.clear.generated._User', {
	extend: 'Ext.data.Model',
	requires: [		
		'Ext.data.Types'
	],
	fields: [
		{
			name: 'id',
			type: Ext.data.Types.STRING,
			useNull: true
		},
		{
			name: 'salary',
			type: Ext.data.Types.NUMBER,
			useNull: true
		}
	]
}
</pre>
 * </p>
 * <p><b>Downloading and Using DTO2ExtJS Annotation Processor in Eclipse Plugin</b></p>
 *  <p>
 *  <li>Copy into your <code>eclipse/plugins folder the jar downloaded from
 *  <a href="http://www.cleartoolkit.com/downloads/plugins/extjs/dto2extjs/com.farata.dto2extjs.asap_4.6.0.jar">http://www.cleartoolkit.com/downloads/plugins/extjs/dto2extjs/com.farata.dto2extjs.asap_4.6.0.jar</a></li>
 *  <li>Copy into <code>WebContent/lib</code> folder of your Dynamic Web Project annotations jar downloaded from
 *  <a href="http://www.cleartoolkit.com/downloads/plugins/extjs/dto2extjs/com.farata.dto2extjs.annotations.jar">http://www.cleartoolkit.com/downloads/plugins/extjs/dto2extjs/com.farata.dto2extjs.annotations.jar</a></li>
 * </p>
 *  <p><b>Downloading and Using Clear Components for Ext JS </b></p>
 *  <p>
 *  To take full advantage of &#64;JSOneToMany you need to use <a href="http://www.cleartoolkit.com/dokuwiki/doku.php?id=clearwiki:40.clear_components_ext">Clear Components for ExtJS</a>.
 *  In this case you get:
 *  <li> automatic injection of reference to the parent record to all "many" associated records on load;</li>
 *  <li> automatic batching of associated changes during <a href="http://docs.sencha.com/ext-js/4-0/#!/api/Ext.data.AbstractStore-method-sync">sync()</a>
 *  of the parent store, i.e. "deep sync".</li>
 *  </p>
 *  <p>
 *  To plug in Clear Components for Ext JS to your Ext JS MVC application copy the contents of ClearJS/src into the web root
 *  of your application and make sure that the main application script starts similar to the following:
 <PRE>// app.js 
 Ext.Loader.setConfig({
	disableCaching: false,
	enabled: true,
	paths  : {
		MyApp: 'app', Clear:'clear'
	}
});
	
Ext.require('Clear.patch.ExtJSPatch');
</PRE>
 *  </p> 
  */ 
@Documented

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface JSIgnore {
	final public static class any {}
}