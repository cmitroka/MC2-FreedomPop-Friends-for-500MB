<%@ Page Title="Template App Site" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true"
    CodeBehind="Default.aspx.cs" Inherits="_Default" %>
<asp:Content ID="Content1" runat="server" contentplaceholderid="HeadContent">
    <title>Main</title>    
</asp:Content>
<asp:Content ID="BodyContent" runat="server" ContentPlaceHolderID="MainContent">
    <center>            
    <h2>My App</h2>
                <hr />
                    <img style="height: auto;width: auto; max-width: 100%" alt="" src="Images/Other/AppDetails.jpg" />
                <br />
                App Description<br />
                <table border="0" style="height: auto;width: auto; text-align:center; max-width: 100%" >
                    <tr>
                        <td>
                            <img alt="" src="Images/Other/AndroidLogo.jpg" class="centeredimage" width="100" /></td>
                        <td>
                            <asp:Image ID="Image3" runat="server" ImageUrl="~/Images/Other/IOSLogo.jpg" CssClass="centeredimage" Width="80px" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <img style="height: auto;width: auto; max-width: 100%" alt="" src="Images/Other/AndroidPhone.jpg" />
                        </td>
                        <td>
                            <img style="height: auto;width: auto; max-width: 100%" alt="" src="Images/Other/IOSPhone.jpg" />
                        </td>
                    </tr>
                    <tr>
                        <td>QR CODE<br />
                            <img style="height: auto;width: auto; max-width: 100%" alt="" src="Images/SiteSpecific/AndroidQRCode.png" /><br />
                            <asp:HyperLink ID="HyperLink2" runat="server" ForeColor="Blue">View in the Google Play Store</asp:HyperLink>
                        </td>
                        <td>QR CODE<br />
                            <img style="height: auto;width: auto; max-width: 100%" alt="" src="Images/SiteSpecific/IOSQRCode.png" /><br />
                            <asp:HyperLink ID="HyperLink1" runat="server" ForeColor="Blue">View in the Apple App Store</asp:HyperLink>
                        </td>
                    </tr>
                </table>
</center>
</asp:Content>
