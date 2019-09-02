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
    </style>
</head>

<body>
    <#macro summarizeEntity name entity>
        <h2>${name}</h2>
        <#if entity.example??>
            <p><b>Example:</b> ${entity.example}</p>
        </#if>
        <#if entity.description??>
            <p><b>Description:</b> ${entity.description}</p>
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
                            ${key}
                            <#if property.type??>
                                (${property.type})
                            <#else>
                                (object)
                            </#if>
                        </td>

                        <td>
                            <#if property.example??>
                            ${property.example}
                            </#if>
                        </td>

                        <td>
                            <#if property.description??>
                                <p>${property.description}</p>
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

    <h1></h1>
    <#assign entitySummaries = root.entitySummaries>
    <#list entitySummaries as key, entitySummary>
        <@summarizeEntity name=key entity=entitySummary>
        </@summarizeEntity>
    </#list>

</body>
</html>