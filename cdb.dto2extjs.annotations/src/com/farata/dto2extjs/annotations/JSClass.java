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
 * Is used to translate the Java class into a matching Ext JS model
 * <p>
 *  <table class="innertable">
 *   <tr>
 *   	<th>Parameter</th><th>Type</th><th>Required</th><th>Description</th>
 *   </tr>
 *   <tr>
 *   	<td><code>value</code></td><td>String</td><td>Optional</td>
 *   	<td>A (fully qualified) name of the model (class). This is a default annotation parameter. 
 *   If you omit this parameter the (fully qualified) Java name is taken verbatim and transformed 
 *   according to the value of the APT option
<code>-Acom.faratasystems.dto2extjs.class-name-transformer</code> (see below)
 *   	</td>
 *   </tr>
 *   <tr>
 *   	<td><code>kind</code></td><td>JSClassKind</td><td>Optional.</td>
 *   	<td>May be deprecate in the future. Do not use.</td>
 *   </tr>
 *   <tr>
 *   	<td><code>ignoreSuperClasses</code></td><td>Class<?>[]</td><td>Optional</td>
 *   	<td>Array of classes and  interfaces in the Java inheritance chain that should be exempted from
 *   translation into corresponding Ext JS models. By design every ancestor or interface implemented by a &#64;JSClass class must also
 *   be annotated as &#64;JSClass or exempt via <code>ignoreSuperClasses</code>. Current version expects that developer
 *   will ignore all interfaces. In the future versions interfaces will be added as mixins.
 *      </td> 
 *   </tr>
 *  </table>
 *  <p><b>Placement of the Generated Models</b>
 *  </p>
 *  <p>Location of the generated models depends on two APT parameters: 
 *  <li><code>-Acom.faratasystems.dto2extjs.output</code> determines the root folder to place generated classes into. Expects path: absolute or relative to
 *  Eclipse workspace, such as <code>c:/dev/MyProject/web</code> or <code>MyProject/WebContent</code></li>
The <li><code>-Acom.faratasystems.dto2extjs.package-path-transformer</code> determines mapping between application namespace and root subfolder. If you
assign AM:app the classes will be create in the WebContent/app and they will belong to <code>AM</code> namespace, starting with <code>Ext.define("AM...</code></li>
</p>
 *  <p><b>Class Names of the Generated Models</b>
 *  </p>
 *  <p>Fully qualified name of the generated model is determined in two steps:
 *  <li>Unless you specify a JavaScript classname as the value or <code>&#64;JSClass</code> it is taken verbatim from the Java source.</li>
 *  <li>Value of the annotation parameter <code>-Acom.faratasystems.dto2extjs.class-name-transformer</code>
 *  is used to transform it further:
 *  <table class="innertable" width="100%">
 *   <tr>
 *   	<th>Setting</th><th>Java Class Name</th><th>Model Name</th>
 *   </tr>
 *   <tr>
 *   	<td><code>AM.model</code></td><td>clear.dto.UserDTO</td><td>AM.model.clear.dto.UserDTO</td>
 *   </tr>
 *   <tr>
 *   	<td><code>AM.model.$1$3<<^com.farata.((\w+\.)*)dto.(\w+)DTO$</code></td><td><code>com.farata.clear.dto.UserDTO</code></td><td><code>AM.model.clear.User</code></td>
 *   </tr>
 *  </table>
 *  </li>
 *  </p>
* <p>To inspect/modify APT parameters in Eclipse go to <i>Project Properties->Java Compiler->Annotation Processing->Processor Options</i>.  
 * </p>
  *  <p><b>Two Generated Classes per One Source</b>
 *  </p>
<p>
Each Java source is translated into a pair of model classes extending one another, herein called bottom and top model: 
<li> a model  <code>original_package_name.<b>generated</b>._OriginalClassName</code> reflects public properties of the original Java class</li>
<li> a descendant model - <code>original_package_name.OriginalClassName</code> for hand-finishing by developers.</li>
The top model is generated only once, the bottom one - every time the annotated Java class is touched.
 * </p> 
 * <p>
 * In the following example Java class <code>clear.dto.UserDTO</code> is annotated by <code>&#64;JSClass</code>.
 * Parameter -Acom.faratasystems.dto2extjs.class-name-transformer is set to AM.model:
 * <pre>
 * package clear.dto;
 * import com.farata.dto2extjs.annotations.JSClass;
 * 	&#64;JSClass
public class UserDTO {
	public String id;
	public Double salary;

	private Date dob;
	public Date getDob() {
		return dob;
	}
	public void setDob(Date value) {
		dob = value;
	}

	&#64;JSOneToMany(storeType="helpdesk.Tickets", foreignKey="userId")
	public List<TicketDTO> tickets;	
}
 * </pre>
 * </p>  
 * <p>
 * First we show the bottom model:
 * </p>
 * <pre>// Generated by DTO2EXTJS from AM.model.clear.dto.UserDTO.java on 2012-02-16T20:45:29-05:00
Ext.define('AM.model.clear.dto.generated._UserDTO', {
	extend: 'Ext.data.Model',
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
		},
  		{
			name: 'dob',
			type: Ext.data.Types.DATE,
			useNull: true
		}
  	],
	hasMany: [
 		{
			model: '',
			name: 'getTickets', 
			primaryKey:'id',
			foreignKey:'userId',
			autoLoad: true,
			storeClassName:'helpdesk.Tickets'
		}
  	],
	requires: [
		
		'Ext.data.Types'
	]
}
 *</pre>
 * <p>
 * And here is the top model:
 <pre>Ext.define('AM.model.clear.dto.UserDTO, {
	extend: 'AM.model.clear.dto.generated._UserDTO'
});	
 </pre>
  </p>
 * <p>
 * Generated models carries a field for every Java public variable or getter/setter pair which
 * has not been excluded by <a href="http://help.faratasystems.com/en_US/cleartoolkit/reference/java/extjs/com/farata/dto2extjs/annotations/JSIgnore.html">&#64;JSIgnore</a>
 * annotation.
 * </p>
 *   
 */ 
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JSClass {
	String value() default "";
	JSClassKind kind() default JSClassKind.DEFAULT;
	Class<?>[] ignoreSuperclasses() default {};
}
