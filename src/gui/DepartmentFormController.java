/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import db.DbException;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentService;

/**
 *
 * @author USER
 */
public class DepartmentFormController implements Initializable {
    
    private Department entity;
    
    private DepartmentService service;
    
    @FXML
    private TextField txtId;
    
    @FXML
    private TextField txtName;
    
    @FXML
    private Label labelErrorName;
    
    @FXML
    private Button btSave;
    
    @FXML
    private Button btCancel;
    
    public void setDepartment(Department entity){
        this.entity = entity;
    }
    public void setDepartmentService(DepartmentService service){
        this.service = service;
    }
    
    @FXML
    public void onBtSaveAction(ActionEvent event){
        if (entity == null) {
            throw new IllegalStateException("entity was null");
        }
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }
        try{
            
        entity = getFormData();
        service.saveOrUpdate(entity);
        Utils.currentStage(event).close();
        
        }catch(DbException e){
            Alerts.showAlert("Error saving object", null, e.getMessage() , Alert.AlertType.ERROR);
        }
        }
    
    private Department getFormData(){
        Department obj = new Department();
        obj.setId(Utils.tryParseToInt(txtId.getId()));
        obj.setName(txtName.getText());
        return obj;
    }
    
    @FXML
    public void onBtCancelAction(ActionEvent event){
        Utils.currentStage(event).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeNodes();
    }
    
    private void initializeNodes(){
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 30);
    }
    
    public void updateFormData(){
        if(entity == null) throw new IllegalStateException();
        
        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getName());
    }
}
