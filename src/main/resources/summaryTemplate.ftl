<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <style>
        table {
            border: 1px solid #1C6EA4;
            background-color: #EEEEEE;
            width: 100%;
            text-align: left;
            border-collapse: collapse;
        }
        table td, table th {
            border: 1px solid #AAAAAA;
            padding: 3px 2px;
        }
        table tbody td {
            font-size: 13px;
        }
        table tr:nth-child(even) {
            background: #D0E4F5;
        }
        table thead {
            background: #1C6EA4;
            background: -moz-linear-gradient(top, #5592bb 0%, #327cad 66%, #1C6EA4 100%);
            background: -webkit-linear-gradient(top, #5592bb 0%, #327cad 66%, #1C6EA4 100%);
            background: linear-gradient(to bottom, #5592bb 0%, #327cad 66%, #1C6EA4 100%);
            border-bottom: 2px solid #444444;
        }
        table thead th {
            font-size: 15px;
            font-weight: bold;
            color: #FFFFFF;
            border-left: 2px solid #D0E4F5;
        }
        table thead th:first-child {
            border-left: none;
        }

        table tfoot {
            font-size: 14px;
            font-weight: bold;
            color: #FFFFFF;
            background: #D0E4F5;
            background: -moz-linear-gradient(top, #dcebf7 0%, #d4e6f6 66%, #D0E4F5 100%);
            background: -webkit-linear-gradient(top, #dcebf7 0%, #d4e6f6 66%, #D0E4F5 100%);
            background: linear-gradient(to bottom, #dcebf7 0%, #d4e6f6 66%, #D0E4F5 100%);
            border-top: 2px solid #444444;
        }
        table tfoot td {
            font-size: 14px;
        }
        table tfoot .links {
            text-align: right;
        }
        table tfoot .links a{
            display: inline-block;
            background: #1C6EA4;
            color: #FFFFFF;
            padding: 2px 8px;
            border-radius: 5px;
        }

        table td {
            vertical-align: top;
        }

        .required {
            color: #CC0000;
        }

        section.entityDefinitions section.definition {
            margin-bottom: 2em;
            border: 2px solid #444444;

        }
    </style>
</head>

<body>
    <#macro summarizeEntity name entity>
        <h3>${name}</h3>
        <#if entity.example??>
            <p><b>Example:</b> ${entity.example}</p>
        </#if>
        <#if entity.description??>
            <div>
                <div><b>Description:</b></div>
            </div>
            <div>
                <#assign description = entity.description?replace("(\n)+", "<br/>",'r')>
                ${description?no_esc}
            </div>
        </#if>
        <table>
            <thead>
                <tr>
                    <th>Property (type)</th>
                    <th>Example</th>
                    <th>Description (nested properties)</th>
                </tr>
            </thead>
            <tbody>
                <#if entity.properties??>
                <#list entity.properties as key, property>
                    <tr>
                        <td>
                            <div class="propertyName">
                            ${key}
                            </div>

                            <div class="propertyType">
                            <#if property.type??>
                                (${property.type})
                            <#else>
                                (object)
                            </#if>
                            </div>
                            <div class="required">
                                <#if property.required>
                                    *required
                                </#if>
                            </div>
                        </td>

                        <td>
                            <#if property.example??>
                            ${property.example}
                            </#if>
                        </td>

                        <td>
                            <#if property.description??>
                                <#assign description = property.description?replace("(\n)+", "<br/>",'r')>
                                <div>${description?no_esc}</div>
                            </#if>
                            <#if property.properties??>
                                <#if (property.properties)?size!=0 >
                                <p>
                                <@summarizeEntity name=key entity=property></@summarizeEntity>
                                </p>
                                </#if>
                            </#if>
                        </td>
                    </tr>
                </#list>
                </#if>
            </tbody>
        </table>
    </#macro>

    <section class="header">
        <h1>${root.title}</h1>
        <p>${root.description}</p>
    </section>

    <section class="entityDefinitions">
    <h2>Entity Definitions</h2>
            <#assign entitySummaries = root.entitySummaries>
            <#list entitySummaries as key, entitySummary>
                <section class="definition">
                    <@summarizeEntity name=key entity=entitySummary/>
                </section>
            </#list>

    </section>

    <section class="endPointDefinition">
    <h2>End Point Definitions</h2>
    <table>
        <thead>
            <tr>
                <th>Title</th>
                <th>URL</th>
                <th>Method</th>
                <th>Description</th>
            </tr>
        </thead>

        <tbody>
            <tr>
                <td>Classification Query</td>
                <td>/api/v1/curations/acmg-classification/query</td>
                <td>POST</td>
                <td>Perform Query for Variant Classifications.</td>
            </tr>
        </tbody>
    </table>
    </section>
</body>
</html>