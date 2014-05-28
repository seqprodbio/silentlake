package ca.on.oicr.silentlake.model;

import io.seqware.webservice.generated.model.Sample;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "sample_hierarchy", uniqueConstraints = @UniqueConstraint(columnNames = { "sample_id", "parent_id" }))
@XmlRootElement
public class SampleHierarchy implements Serializable {
   private static final long serialVersionUID = 1L;

   @JoinColumn(name = "sample_id", referencedColumnName = "sample_id")
   @ManyToOne(optional = false)
   private Sample sampleId;

   @JoinColumn(name = "parent_id", referencedColumnName = "sample_id")
   @ManyToOne
   private Sample parentId;

   public SampleHierarchy() {
   }

   public Sample getSampleId() {
      return sampleId;
   }

   public void setSampleId(Sample sampleId) {
      this.sampleId = sampleId;
   }

   public Sample getParentId() {
      return parentId;
   }

   public void setParentId(Sample parentId) {
      this.parentId = parentId;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((sampleId == null) ? 0 : sampleId.hashCode());
      result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      SampleHierarchy other = (SampleHierarchy) obj;
      if (sampleId == null) {
         if (other.sampleId != null) return false;
      } else if (!sampleId.equals(other.sampleId)) return false;
      if (parentId == null) {
         if (other.parentId != null) return false;
      } else if (!parentId.equals(other.parentId)) return false;
      return true;
   }

}
